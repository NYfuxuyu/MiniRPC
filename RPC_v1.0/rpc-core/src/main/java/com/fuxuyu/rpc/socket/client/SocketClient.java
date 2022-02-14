package com.fuxuyu.rpc.socket.client;

import com.fuxuyu.rpc.RpcClient;
import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.entity.RpcResponse;
import com.fuxuyu.rpc.enumeration.ResponseCode;
import com.fuxuyu.rpc.enumeration.RpcError;
import com.fuxuyu.rpc.enumeration.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public SocketClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    @Override
    public Object sendRequest(RpcRequest rpcRequest)  {
/**
 * socket套接字实现TCP网络传输
 * try()中一般放对资源的申请，若{}出现异常，()资源会自动关闭
 */
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            RpcResponse rpcResponse =(RpcResponse) objectInputStream.readObject();
            if(rpcResponse == null){
                logger.error("服务调用失败，service:" + rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()){
                logger.error("服务调用失败，service:{} response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：" + e);
           throw new RpcException("服务调用失败",e);
        }

    }

}
