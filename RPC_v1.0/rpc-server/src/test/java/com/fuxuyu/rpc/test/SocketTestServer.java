package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.entity.annotation.ServiceScan;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.serializer.impl.ProtostuffSerializer;
import com.fuxuyu.rpc.transport.RpcServer;
import com.fuxuyu.rpc.transport.socket.server.SocketServer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:03
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();

    }
}
