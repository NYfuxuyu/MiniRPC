package com.fuxuyu.rpc.api;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/4 21:46
 */


public interface HelloService {
    /**
     * 通用接口方法
     * @param object
     * @return
     */
    String Hello(HelloObject object);
}
