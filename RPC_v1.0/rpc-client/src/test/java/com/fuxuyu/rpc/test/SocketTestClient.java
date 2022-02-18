package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.ByeService;
import com.fuxuyu.rpc.loadbalancer.impl.RoundRobinLoadBalancer;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.transport.proxy.RpcClientProxy;
import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;

import com.fuxuyu.rpc.serializer.impl.ProtostuffSerializer;
import com.fuxuyu.rpc.transport.socket.client.SocketClient;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:07
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.PROTOBUF_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(205326, "hello Rpc_v1.0");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.Hello(helloObject);
        System.out.println(res);
        ByeService byeService = proxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("socket"));

    }
}
