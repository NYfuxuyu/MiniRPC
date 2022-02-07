package com.fuxuyu.rpc.enumeration;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/7 17:10
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcError error, String detail){
        super(error.getMessage() + ":" + detail);
    }
    public RpcException(String message, Throwable cause){
        super(message, cause);
    }

    public RpcException(RpcError error){
        super(error.getMessage());
    }
}
