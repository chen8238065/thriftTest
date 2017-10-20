package com.chapa.demo.thrift.server.common;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import static com.chapa.demo.thrift.common.Init.getServerTTransport;

/**
 * Created by chapa on 17-10-13.
 */
public class TServerFactory {
    private String serverType;
    private TServerTransport serverTransport;
    private TProtocolFactory protocolFactory;
    private TTransportFactory transportFactory;

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public TServerTransport getServerTransport() {
        return serverTransport;
    }

    public void setServerTransport(TServerTransport serverTransport) {
        this.serverTransport = serverTransport;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public TTransportFactory getTransportFactory() {
        return transportFactory;
    }

    public void setTransportFactory(TTransportFactory transportFactory) {
        this.transportFactory = transportFactory;
    }

    public  TServer getTServer(TProcessor tprocessor ) throws Exception {
        TServer server=null;
        TServerTransport serverTransport=getServerTransport();

        switch (getServerType()){
            case "TSimpleServer"://
                TServer.Args tArgs = new TServer.Args(serverTransport);
                tArgs.processor(tprocessor);
                tArgs.protocolFactory(getProtocolFactory());
                server = new TSimpleServer(tArgs);
                break;
            case "TThreadPoolServer"://
                TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
                        serverTransport);
                ttpsArgs.processor(tprocessor);
                ttpsArgs.protocolFactory(getProtocolFactory());
                // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
                server = new TThreadPoolServer(ttpsArgs);
                break;
            case "TNonblockingServer":
            {
                TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args((TNonblockingServerTransport) serverTransport);
                tnbArgs.processor(tprocessor);
                tnbArgs.transportFactory(getTransportFactory());
                tnbArgs.protocolFactory(getProtocolFactory());
                // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
                server = new TNonblockingServer(tnbArgs);
                break;
            }
            case "THsHaServer":
            {
                THsHaServer.Args thhsArgs = new THsHaServer.Args((TNonblockingServerTransport) serverTransport);
                thhsArgs.processor(tprocessor);
                thhsArgs.transportFactory(getTransportFactory());
                thhsArgs.protocolFactory(getProtocolFactory());
                //半同步半异步的服务模型
                server = new THsHaServer(thhsArgs);
                break;
            }
            case "TThreadedSelectorServer":
            {
                TThreadedSelectorServer.Args thhsArgs = new TThreadedSelectorServer.Args((TNonblockingServerTransport) serverTransport);
                thhsArgs.processor(tprocessor);
                thhsArgs.transportFactory(getTransportFactory());
                thhsArgs.protocolFactory(getProtocolFactory());
                //半同步半异步的服务模型
                server = new TThreadedSelectorServer(thhsArgs);
                break;
            }
            default:
                throw new Exception("server model  is wrong");

        }
        return server;
    }
}
