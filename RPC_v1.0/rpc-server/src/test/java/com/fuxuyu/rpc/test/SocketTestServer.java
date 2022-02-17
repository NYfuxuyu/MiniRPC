package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.serializer.impl.ProtostuffSerializer;
import com.fuxuyu.rpc.transport.socket.server.SocketServer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:03
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.PROTOBUF_SERIALIZER);

        socketServer.publishService(helloService, HelloService.class);

    }
}
