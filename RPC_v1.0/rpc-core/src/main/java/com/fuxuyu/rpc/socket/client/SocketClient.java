package com.fuxuyu.rpc.socket.client;

import com.fuxuyu.rpc.RpcClient;
import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.entity.RpcResponse;
import com.fuxuyu.rpc.enumeration.ResponseCode;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.exception.RpcException;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.socket.util.ObjectReader;
import com.fuxuyu.rpc.socket.util.ObjectWriter;
import com.fuxuyu.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.net.Socket;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/5 15:14
 */
public class SocketClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    public SocketClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    @Override
    public Object sendRequest(RpcRequest rpcRequest)  {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
/**
 * socket套接字实现TCP网络传输
 * try()中一般放对资源的申请，若{}出现异常，()资源会自动关闭
 */
        try (Socket socket = new Socket(host, port)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            /*if(rpcResponse == null){
                logger.error("服务调用失败，service:" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()){
                logger.error("服务调用失败，service:{} response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }*/
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();

        } catch (IOException  e) {
            logger.error("调用时有错误发生：" + e);
           throw new RpcException("服务调用失败",e);
        }

    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}
