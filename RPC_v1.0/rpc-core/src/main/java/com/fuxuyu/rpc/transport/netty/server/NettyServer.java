package com.fuxuyu.rpc.transport.netty.server;


import com.fuxuyu.rpc.hook.ShutdownHook;
import com.fuxuyu.rpc.provider.ServiceProvider;
import com.fuxuyu.rpc.provider.ServiceProviderImpl;
import com.fuxuyu.rpc.registry.impl.NacosServiceRegistry;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.transport.RpcServer;
import com.fuxuyu.rpc.codec.CommonDecoder;
import com.fuxuyu.rpc.codec.CommonEncoder;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 18:12
 */
public class NettyServer implements RpcServer {
    public static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private CommonSerializer serializer;

    private final String host;
    private final int port;

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;
    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
    }

    /**
     * 将服务注册保存在本地的注册表然后注册到nacos
     * @param service
     * @param serviceClass
     * @param <T>
     */
    @Override
    public <T> void publishService(T service, Class<T> serviceClass) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service, serviceClass);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }
    @Override
    public void start() {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //用来处理客户端连接的主线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于连接后处理IO事件的从线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道类型
                    .channel(NioServerSocketChannel.class)
                    //日志打印方式
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //配置ServerChannel参数，服务端接受连接的最大队列长度，如果队列已满，客户端连接将被拒绝
                    .option(ChannelOption.SO_BACKLOG, 256)
                    //启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s即2小时。
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //配置Channel参数，nodelay没有延迟，true就代表禁用Nagle算法，减小传输延迟。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //初始化Handler,设置Handler操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //初始化管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());

                        }
                    });
            //绑定端口，启动Netty，sync()代表阻塞主Server线程，以执行Netty线程，如果不阻塞Netty就直接被下面shutdown了
            ChannelFuture future = serverBootstrap.bind(host,port).sync();
            //添加注销服务的钩子，服务端关闭时才会执行
            ShutdownHook.getShutdownHook().addClearAllHook();
            //等确定通道关闭了，关闭future回到主Server线程
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生", e);
        } finally {
            //优雅关闭Netty服务端且清理掉内存，shutdownGracefully()
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }


}