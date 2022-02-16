package com.fuxuyu.rpc.util;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 管理Nacos连接的工具类
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 18:55
 */
public class NacosUtil {
    public static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);
    private static final String SERVER_ADDR = "127.0.0.1:8848";

    /**
     * 连接到nacos创建命名空间
     * @return
     */
    public static NamingService getNacosNamingService() {
        try {
            return NamingFactory.createNamingService(SERVER_ADDR);
        }catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    /**
     * 注册到nacos
     * @param namingService
     * @param serviceName
     * @param inetSocketAddress
     * @throws NacosException
     */
    public static void registerService(NamingService namingService, String serviceName, InetSocketAddress inetSocketAddress) throws NacosException {
        namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
    }

    /**
     * 获取所有提供该服务的服务端地址
     * @param namingService
     * @param serviceName
     * @return
     * @throws NacosException
     */
    public static List<Instance> getAllInstance(NamingService namingService, String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}
