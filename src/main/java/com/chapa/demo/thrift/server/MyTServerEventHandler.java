package com.chapa.demo.thrift.server;

import com.chapa.demo.thrift.server.common.ServerInit;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by chapa on 17-10-11.
 * 通过事件监听机制 来获取 client 信息
 */
class MyTServerEventHandler implements TServerEventHandler {
    /**
     * 服务成功启动后执行
     */
    public void preServe()
    {
        System.out.println("Start server on port " + ServerInit.SERVER_PORT + " ...");
    }

    /**
     * 每一个tcp链接时,创建Context的时候，触发
     */
    public ServerContext createContext(TProtocol input, TProtocol output)
    {
        System.out.println("createContext ... ");
        return null;
    }

    /**
     *  每一个tcp链接关闭时,删除Context的时候，触发
     */
    public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output)
    {
        System.out.println("deleteContext ... ");
    }

    /**
     * 调用RPC服务的时候触发
     * 每调用一次方法，就会触发一次
     */
    public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport)
    {
        /**
         * 把TTransport对象转换成TSocket，然后在TSocket里面获取Socket，就可以拿到客户端IP
         */
        TSocket socket = (TSocket)inputTransport;
        System.out.println(socket.getSocket().getRemoteSocketAddress());
        System.out.println("method invoke ... ");
    }
}