package com.fuxuyu.rpc.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/18 13:26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScan {
    public String value() default "";
}
