package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.entity.annotation.ServiceScan;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.transport.RpcServer;
import com.fuxuyu.rpc.transport.netty.server.NettyServer;

import com.fuxuyu.rpc.serializer.impl.ProtostuffSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 22:47
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
