package com.fuxuyu.rpc.client;

import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 15:07
 */
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private Integer port;

    public RpcClientProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }
    //抑制编译器产生警告信息
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        //创建代理对象
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        //进行远程调用的客户端
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest, host, port)).getData();

    }
}
