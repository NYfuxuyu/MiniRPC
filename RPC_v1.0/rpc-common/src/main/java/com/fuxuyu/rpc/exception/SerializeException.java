package com.fuxuyu.rpc.exception;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/15 10:02
 */
public class SerializeException extends RuntimeException{
    public SerializeException(String msg){
        super(msg);
    }
}
