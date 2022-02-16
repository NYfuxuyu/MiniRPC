package com.fuxuyu.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/16 20:28
 */
public interface ServiceDiscovery {
    /**
     * 根据服务名称查找服务端地址
     * @param serviceName
     * @return
     */
    InetSocketAddress lookupService(String serviceName);
}
