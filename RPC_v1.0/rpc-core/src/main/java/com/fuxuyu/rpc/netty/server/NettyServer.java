package com.fuxuyu.rpc.netty.server;


import com.fuxuyu.rpc.RpcServer;
import com.fuxuyu.rpc.codec.CommonDecoder;
import com.fuxuyu.rpc.codec.CommonEncoder;
import com.fuxuyu.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 18:12
 */
public class NettyServer implements RpcServer {
    public static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Override
    public void start(int port) {
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
                            pipeline.addLast(new CommonEncoder(new JsonSerializer()))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());

                        }
                    });
            //绑定端口，启动Netty，sync()代表阻塞主Server线程，以执行Netty线程，如果不阻塞Netty就直接被下面shutdown了
            ChannelFuture future = serverBootstrap.bind(port).sync();
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
}
