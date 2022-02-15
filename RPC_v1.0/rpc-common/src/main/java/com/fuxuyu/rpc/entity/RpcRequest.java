package com.fuxuyu.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/4 23:08
 */
@Data
//使用创建者模式，一次性给所有变量初始赋值
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 请求号
     */
    private String requestId;

    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 待调用方法的参数
     */
    private Object[] parameters;
    /**
     * 待调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
