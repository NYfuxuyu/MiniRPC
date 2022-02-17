package com.fuxuyu.rpc.registry.impl;



import com.alibaba.nacos.api.exception.NacosException;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import com.fuxuyu.rpc.registry.ServiceRegistry;
import com.fuxuyu.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 14:25
 */
public class NacosServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);





    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            NacosUtil.registerService(serviceName, inetSocketAddress);
        }catch (NacosException e) {
            logger.error("注册服务时有错误发生" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }

    }

}
