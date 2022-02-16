package com.fuxuyu.rpc.transport.socket.server;

import com.fuxuyu.rpc.handler.RequestHandler;
import com.fuxuyu.rpc.entity.RpcRequest;
import com.fuxuyu.rpc.serializer.CommonSerializer;
import com.fuxuyu.rpc.transport.socket.util.ObjectReader;
import com.fuxuyu.rpc.transport.socket.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @date 2022/2/8 22:39
 */
public class RequestHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;

    private CommonSerializer serializer;
    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object response = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, response, serializer);
        }catch (IOException e){
            logger.info("调用或发送时发生错误：" + e);
        }
    }
}
