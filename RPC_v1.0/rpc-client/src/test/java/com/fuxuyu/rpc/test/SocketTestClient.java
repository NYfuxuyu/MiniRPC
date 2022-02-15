package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.RpcClientProxy;
import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;

import com.fuxuyu.rpc.serializer.HessianSerializer;
import com.fuxuyu.rpc.serializer.KryoSerializer;
import com.fuxuyu.rpc.serializer.ProtostuffSerializer;
import com.fuxuyu.rpc.socket.client.SocketClient;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:07
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(205326, "hello Rpc_v1.0");
        String res = helloService.Hello(helloObject);
        System.out.println(res);
    }
}
