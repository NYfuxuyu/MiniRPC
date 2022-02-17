package com.fuxuyu.rpc.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fuxuyu.rpc.loadbalancer.LoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/17 21:25
 */
public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}
