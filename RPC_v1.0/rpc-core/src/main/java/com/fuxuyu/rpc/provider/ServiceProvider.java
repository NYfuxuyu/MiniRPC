package com.fuxuyu.rpc.provider;

/**保存和提供服务实例对象
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/16 15:31
 */
public interface ServiceProvider {

   /* <T> void addServiceProvider(T service);*/
   <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);
}
