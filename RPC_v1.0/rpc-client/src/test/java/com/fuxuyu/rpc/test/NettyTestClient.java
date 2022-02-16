package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.transport.RpcClient;
import com.fuxuyu.rpc.transport.RpcClientProxy;
import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.transport.netty.client.NettyClient;
import com.fuxuyu.rpc.serializer.impl.ProtostuffSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 22:45
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1212, "this is netty style");
        String res = helloService.Hello(object);
        System.out.println(res);
    }
}
