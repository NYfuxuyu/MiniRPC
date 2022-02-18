package com.fuxuyu.rpc.transport.socket.server;


import com.fuxuyu.rpc.hook.ShutdownHook;
import com.fuxuyu.rpc.provider.ServiceProvider;
import com.fuxuyu.rpc.provider.ServiceProviderImpl;
import com.fuxuyu.rpc.handler.RequestHandler;
import com.fuxuyu.rpc.registry.impl.NacosServiceRegistry;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.transport.AbstractRpcServer;
import com.fuxuyu.rpc.transport.RpcServer;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.factory.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 17:54
 */
public class SocketServer extends AbstractRpcServer {

   /* private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;*/
    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();


    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializerCode){
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        serializer = CommonSerializer.getByCode(serializerCode);
        //创建线程池
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        //自动注册服务
        scanServices();
    }

@Override
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            //添加钩子，服务端关闭时会注销服务
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                logger.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            logger.info("服务器启动时有错误发生：" + e);
        }
    }

}
