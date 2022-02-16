package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/16 22:21
 */
public class HelloServiceImpl2 implements HelloService {
    public static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String Hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "本次处理来自Socket服务," + "调用的返回值为：id = " + object.getId();
    }
}
