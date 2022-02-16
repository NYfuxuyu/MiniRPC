package com.fuxuyu.rpc.transport;

import com.fuxuyu.rpc.serializer.CommonSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/12 17:48
 */
public interface RpcServer {

    void start();
    void setSerializer(CommonSerializer serializer);

    /**
     * 像nacos注册服务
     * @param service
     * @param serviceClass
     * @param <T>
     */
    <T> void publishService(T service, Class<T> serviceClass);
}
