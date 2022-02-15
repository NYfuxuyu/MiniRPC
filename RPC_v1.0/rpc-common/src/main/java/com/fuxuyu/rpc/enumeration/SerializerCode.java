package com.fuxuyu.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 22:15
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int code;
}
