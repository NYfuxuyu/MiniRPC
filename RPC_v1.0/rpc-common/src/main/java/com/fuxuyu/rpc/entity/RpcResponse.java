package com.fuxuyu.rpc.entity;

import com.fuxuyu.rpc.enumeration.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 12:04
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {
    /**
     * 响应对应的请求号
     */
    private String requestId;
    /**
     *响应状态码
     */
    private Integer statusCode;
    /**
     *响应状态码对应的信息
     */
    private String message;
    /**
     *成功时的响应数据
     */
    private T data;

    /**
     * 成功的时候返回的对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data, String requestId){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setRequestId(requestId);
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId){
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
