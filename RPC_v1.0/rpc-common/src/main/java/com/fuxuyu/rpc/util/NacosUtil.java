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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 管理Nacos连接的工具类
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 18:55
 */
public class NacosUtil {
    public static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);
    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;
    private static final Set<String> serviceNames = new HashSet<>();
    private static InetSocketAddress address;

    static {
        namingService = getNacosNamingService();
    }
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
     * @param serviceName
     * @param address
     * @throws NacosException
     */
    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
        NacosUtil.address = address;
        //保存注册的服务名
        serviceNames.add(serviceName);
    }

    /**
     * 获取所有提供该服务的服务端地址
     * @param serviceName
     * @return
     * @throws NacosException
     */
    public static List<Instance> getAllInstance(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }


    /**
     * 注销服务
     */
    public static void clearRegistry() {
        if(!serviceNames.isEmpty() && address != null) {
            String host = address.getHostName();
            int port = address.getPort();
            //利用迭代器迭代注销
            Iterator<String> iterator = serviceNames.iterator();
            while (iterator.hasNext()) {
                String serviceName = iterator.next();
                try {
                    //注销服务
                    namingService.deregisterInstance(serviceName, host, port);
                }catch (NacosException e) {
                    logger.error("注销服务{}失败", serviceName, e);
                }
            }
        }
    }

}
