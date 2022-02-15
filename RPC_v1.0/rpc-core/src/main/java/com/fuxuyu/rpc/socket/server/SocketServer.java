package com.fuxuyu.rpc.socket.server;


import com.fuxuyu.rpc.RequestHandler;
import com.fuxuyu.rpc.RpcServer;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.util.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 17:54
 */
public class SocketServer implements RpcServer {
   /* private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;*/
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private CommonSerializer serializer;

    public SocketServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        /**
         * 设置上限为100个线程的阻塞队列
         *//*
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
*/
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");

    }
@Override
    public void start(int port){
    if (serializer == null){
        logger.error("未设置序列化器");
        throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
    }
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务器启动……");
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                logger.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            logger.info("服务器启动时有错误发生：" + e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
