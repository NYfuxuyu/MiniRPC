package com.fuxuyu.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
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


}
