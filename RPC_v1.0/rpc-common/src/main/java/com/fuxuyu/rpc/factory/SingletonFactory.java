package com.fuxuyu.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/17 9:26
 */
public class SingletonFactory {
    private static Map<Class, Object> objectMap = new HashMap<>();

    private SingletonFactory(){}

    /**
     *  获取单例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        //锁住类保证线程安全
        synchronized (clazz){
            if(instance == null){
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                }catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }
}
