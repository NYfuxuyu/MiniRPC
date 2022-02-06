package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.client.RpcClientProxy;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 21:07
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(1000, "hello Rpc_v1.0");
        String res = helloService.Hello(helloObject);
        System.out.println(res);
    }
}
