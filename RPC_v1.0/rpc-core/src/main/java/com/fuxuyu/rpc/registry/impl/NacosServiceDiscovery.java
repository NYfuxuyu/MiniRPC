package com.fuxuyu.rpc.registry.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fuxuyu.rpc.loadbalancer.LoadBalancer;
import com.fuxuyu.rpc.loadbalancer.impl.RandomLoadBalancer;
import com.fuxuyu.rpc.registry.ServiceDiscovery;
import com.fuxuyu.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/16 20:24
 */
public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);
    private final LoadBalancer loadBalancer;
    public NacosServiceDiscovery(LoadBalancer loadBalancer){
        if (loadBalancer == null){
            this.loadBalancer = new RandomLoadBalancer();
        }else {
            this.loadBalancer = loadBalancer;
        }
    }
    /**
     * 根据服务名称从注册中心获取到一个服务提供者的地址
     * @param serviceName
     * @return
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            //负载均衡获取一个服务实体
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());

        } catch (NacosException e) {
            logger.error("获取服务时有错误发生", e);
        }
        return null;
    }
}
