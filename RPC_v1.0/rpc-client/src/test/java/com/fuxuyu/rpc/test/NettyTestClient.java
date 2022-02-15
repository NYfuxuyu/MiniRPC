package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.RpcClient;
import com.fuxuyu.rpc.RpcClientProxy;
import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.netty.client.NettyClient;
import com.fuxuyu.rpc.serializer.HessianSerializer;
import com.fuxuyu.rpc.serializer.KryoSerializer;
import com.fuxuyu.rpc.serializer.ProtostuffSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 22:45
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1212, "this is netty style");
        String res = helloService.Hello(object);
        System.out.println(res);
    }
}
