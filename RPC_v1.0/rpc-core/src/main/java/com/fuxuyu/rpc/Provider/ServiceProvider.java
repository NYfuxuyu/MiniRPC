package com.fuxuyu.rpc.Provider;

/**保存和提供服务实例对象
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 15:31
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service);

    Object getServiceProvider(String serviceName);
}
