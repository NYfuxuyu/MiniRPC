package com.fuxuyu.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 12:14
 */
@AllArgsConstructor
@Getter

public enum ResponseCode {
    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    METHOD_NOT_FOUND(500,"未找到指定方法"),
    ClASS_NOT_FOUND(500,"未找到指定类");

    private final int code;
    private final String message;
}
