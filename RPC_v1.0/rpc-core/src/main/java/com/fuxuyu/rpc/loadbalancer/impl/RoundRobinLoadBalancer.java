package com.fuxuyu.rpc.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fuxuyu.rpc.loadbalancer.LoadBalancer;

import java.util.List;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/17 21:44
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    /**
     * index表示当前选到了第几个服务器，并且每次选择后都会自增一
     */
    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if(index >= instances.size()){
            index %= instances.size();
        }
        return instances.get(index++);
    }
}
