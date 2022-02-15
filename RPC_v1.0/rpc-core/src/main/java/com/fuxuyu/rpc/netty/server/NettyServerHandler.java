package com.fuxuyu.rpc.netty.server;

import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.entity.RpcResponse;
import com.fuxuyu.rpc.registry.DefaultServiceRegistry;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 19:44
 */
public class NettyServerHandler  extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;
    static{
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务端接收到请求：{}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
          /*  Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));*/
            Object response = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(response);
            //添加一个监听器到channelfuture来检测是否所有的数据包都发出，然后关闭通道
            future.addListener(ChannelFutureListener.CLOSE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}
