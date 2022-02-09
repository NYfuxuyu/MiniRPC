package com.fuxuyu.rpc.registry;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/7 17:58
 */
public interface ServiceRegistry {
    /**
     * @description 将一个服务注册进注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
    <T> void register(T service);

    /**
     * @description 根据服务名获取服务实体
     * @param serviceName 服务名称
     * @return [java.lang.Object] 服务实体
     */

    Object getService(String serviceName);
}
