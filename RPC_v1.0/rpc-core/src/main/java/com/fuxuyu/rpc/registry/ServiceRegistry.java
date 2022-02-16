package com.fuxuyu.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 14:16
 */
public interface ServiceRegistry{
    /**
     * 将服务注册到注册表
     * @param serviceName
     * @param inetSocketAddress
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 根据服务名查找服务端地址
     * @param serviceName
     * @return
     */
    InetSocketAddress lookupService(String serviceName);
}
