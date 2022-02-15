package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.netty.server.NettyServer;
import com.fuxuyu.rpc.registry.DefaultServiceRegistry;
import com.fuxuyu.rpc.registry.ServiceRegistry;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 22:47
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
