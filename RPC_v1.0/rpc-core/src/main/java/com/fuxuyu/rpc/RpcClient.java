package com.fuxuyu.rpc;

import com.fuxuyu.rpc.entity.RpcRequest;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/12 17:30
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
