package com.fuxuyu.rpc.test;

import com.fuxuyu.rpc.api.HelloObject;
import com.fuxuyu.rpc.api.HelloService;
import com.fuxuyu.rpc.entity.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/4 22:57
 */
@Service
public class HelloServiceImpl implements HelloService {
    public static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String Hello(HelloObject object) {
        /*logger.info("接受到：" + object.getMessage());
        return "调用的返回值为：id = " + object.getId();*/
        logger.info("接收到消息：{}", object.getMessage());
        return "成功调用hello()方法";
    }
}
