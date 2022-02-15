package com.fuxuyu.rpc;

import com.fuxuyu.rpc.serializer.CommonSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/12 17:48
 */
public interface RpcServer {

    void start(int port);
    void setSerializer(CommonSerializer serializer);
}
