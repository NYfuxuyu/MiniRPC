package com.fuxuyu.rpc.transport;

import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.serializer.CommonSerializer;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/12 17:30
 */
public interface RpcClient {
    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
