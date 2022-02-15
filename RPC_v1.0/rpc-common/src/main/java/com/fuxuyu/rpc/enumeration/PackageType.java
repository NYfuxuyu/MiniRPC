package com.fuxuyu.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 21:23
 */

@Getter
@AllArgsConstructor
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}

