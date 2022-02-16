package com.fuxuyu.rpc.serializer.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.fuxuyu.rpc.enumeration.SerializerCode;
import com.fuxuyu.rpc.exception.SerializeException;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/15 10:43
 */
public class HessianSerializer implements CommonSerializer {
    private static final Logger logger = LoggerFactory.getLogger(HessianSerializer.class);
    @Override
    public byte[] serialize(Object obj) {
        HessianOutput hessianOutput = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            logger.error("序列化时有错误发生" + e);
            throw new SerializeException("序列化时有错误发生");
        }finally {
            if(hessianOutput != null){
                try {
                    hessianOutput.close();
                }catch (IOException e){
                    logger.error("关闭output流时有错误发生" + e);
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        HessianInput hessianInput = null;
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)){
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        }catch (IOException e){
            logger.error("反序列化时有错误发生" + e);
            throw new SerializeException("反序列化时有错误发生");
        }finally {
            if(hessianInput != null) {
                hessianInput.close();
            }
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.HESSIAN.getCode();
    }
}
