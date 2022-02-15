package com.fuxuyu.rpc.serializer;



/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/14 21:09
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes, Class<?> clazz);
    int getCode();
    static CommonSerializer getByCode(int code){
        switch (code){
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }


}
