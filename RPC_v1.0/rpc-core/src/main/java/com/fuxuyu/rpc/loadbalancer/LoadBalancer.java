package com.fuxuyu.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/17 21:04
 */
public interface LoadBalancer {

    Instance select(List<Instance> instances);
}
