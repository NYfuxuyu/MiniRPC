package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.registry.DefaultServiceRegistry;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.socket.server.SocketServer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:03
 */
public class SocketTestServer {
    public static void main(String[] args) {
        //创建服务对象
        HelloService helloService = new HelloServiceImpl();
        //创建服务容器
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //注册服务对象到服务容器中
        serviceRegistry.register(helloService);
        //将服务容器纳入到服务端
        SocketServer socketServer = new SocketServer(serviceRegistry);
        //启动服务端
        socketServer.start(9000);

    }
}
