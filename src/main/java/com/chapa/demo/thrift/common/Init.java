package com.chapa.demo.thrift.common;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.*;
import org.apache.thrift.server.*;
import org.apache.thrift.transport.*;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by CHEN on 2016/3/8.
 */
public abstract class Init {
    private static Properties  pro = new Properties();
    static{
        try {
            String userDir = System.getProperty("user.dir");
            String rootPath = userDir + "/src/main/resources/";
            String proPath = rootPath+ "thrift.properties";
            FileInputStream in = new FileInputStream(proPath);
            pro.load(in);
            in.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public static final String SERVER_IP = pro.getProperty("SERVER_IP");
    public static final int SERVER_PORT =  Integer.parseInt(pro.getProperty("SERVER_PORT"));
    public static final int TIMEOUT =   Integer.parseInt(pro.getProperty("TIMEOUT"));
    public static final String protocolStr =   pro.getProperty("protocol").trim();
    public static final String transportServerStr =  pro.getProperty("server_transport").trim();
    public static final String transportClientStr =  pro.getProperty("client_transport").trim();
    public static final String serverStr =  pro.getProperty("server").trim();


    public static TTransportFactory getTTransportFactory() throws Exception {
        TTransportFactory fac=null;
        switch (transportClientStr){
            case "TFramedTransport"://
            case "TNonblockingSocket":
                fac=new TFramedTransport.Factory();
                break;
            case "TZlibTransport"://
                fac=new TZlibTransport.Factory();
                break;
            case "TFastFramedTransport"://
                fac=new TFastFramedTransport.Factory();
                break;
            case "TSimpleJSONProtocol"://
//                fac=new TSimpleJSONProtocol.Factory();
                break;
            default:
                throw new Exception("transport factory is wrong");

        }
        return fac;
    }

    public static TProtocolFactory getTProtocolFactory() throws Exception {
        TProtocolFactory fac=null;
        switch (protocolStr){
            case "TBinaryProtocol"://二进制格式
                fac=new TBinaryProtocol.Factory();
                break;
            case "TCompactProtocol"://压缩格式
                fac=new TCompactProtocol.Factory();
                break;
            case "TJSONProtocol"://JSON格式
                fac=new TJSONProtocol.Factory();
                break;
            case "TSimpleJSONProtocol"://提供JSON只写协议, 生成的文件很容易通过脚本语言解析
                fac=new TSimpleJSONProtocol.Factory();
                break;
            default:
                throw new Exception("protocol factory is wrong");

        }
        return fac;
    }

    public static TProtocol getTProtocol(TTransport transport) throws Exception {
        TProtocol protocol=null;
        switch (protocolStr){
            case "TBinaryProtocol"://二进制格式
                protocol=new TBinaryProtocol(transport);
                break;
            case "TCompactProtocol"://压缩格式
                protocol=new TCompactProtocol(transport);
                break;
            case "TJSONProtocol"://JSON格式
                protocol=new TJSONProtocol(transport);
                break;
            case "TSimpleJSONProtocol"://提供JSON只写协议, 生成的文件很容易通过脚本语言解析
                protocol=new TSimpleJSONProtocol(transport);
                break;
            case "TTupleProtocol"://继承于TCompactProtocol，Struct的编解码时使用更省地方
                protocol=new TTupleProtocol(transport);
                break;
            default:
                throw new Exception("protocol is wrong");

        }
        return protocol;
    }

    public static TServerTransport getServerTTransport(){
        TServerTransport transport = null;
        try {
            switch (transportServerStr){
                case "TServerSocket"://
                    transport=new TServerSocket(SERVER_PORT);
                    break;
                case "TNonblockingServerSocket"://
                    transport=new TNonblockingServerSocket(SERVER_PORT);
                    break;
                case "TZlibTransport"://
//                    transport=new TZlibTransport(new TServerSocket(SERVER_PORT));
                    break;
                case "TFramedTransport"://
                    transport=null;
                    break;
                default:
                    throw new Exception("TServerTransport is wrong");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transport;
    }

    public static TTransport getClientTTransport(){
        TTransport transport = null;
        try {
            switch (transportClientStr){
                case "TSocket"://
                    transport=new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
                    break;
                case "TNonblockingSocket"://
                    transport=new TNonblockingSocket(SERVER_IP,
                            SERVER_PORT, TIMEOUT);
                    break;
                case "TFileTransport"://
                    transport=new TFileTransport("",false);
                    break;
                case "TZlibTransport"://
                    transport=new TZlibTransport(new TSocket(SERVER_IP,
                            SERVER_PORT, TIMEOUT));
                    break;
                case "TFramedTransport"://
                    transport=new TFramedTransport(new TSocket(SERVER_IP,
                            SERVER_PORT, TIMEOUT));
                    break;
                case "THttpClient"://
                    transport=new THttpClient("");
                    break;
                default:
                    throw new Exception("client transport is wrong");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transport;
    }

    public static TServer getTServer(TProcessor tprocessor ) throws Exception {
        TServer server=null;
        TServerTransport serverTransport=getServerTTransport();

        switch (serverStr){
            case "TSimpleServer"://
                TServer.Args tArgs = new TServer.Args(serverTransport);
                tArgs.processor(tprocessor);
                tArgs.protocolFactory(getTProtocolFactory());
                server = new TSimpleServer(tArgs);
                break;
            case "TThreadedServer"://

                break;
            case "TThreadPoolServer"://
                TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
                        serverTransport);
                ttpsArgs.processor(tprocessor);
                ttpsArgs.protocolFactory(getTProtocolFactory());
                // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
                server = new TThreadPoolServer(ttpsArgs);
                break;
            case "TNonblockingServer":
            {
                TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args((TNonblockingServerTransport) serverTransport);
                tnbArgs.processor(tprocessor);
                tnbArgs.transportFactory(getTTransportFactory());
                tnbArgs.protocolFactory(getTProtocolFactory());
                // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
                server = new TNonblockingServer(tnbArgs);
                break;
            }
            case "THsHaServer":
            {
                THsHaServer.Args thhsArgs = new THsHaServer.Args((TNonblockingServerTransport) serverTransport);
                thhsArgs.processor(tprocessor);
                thhsArgs.transportFactory(getTTransportFactory());
                thhsArgs.protocolFactory(getTProtocolFactory());
                //半同步半异步的服务模型
                server = new THsHaServer(thhsArgs);
                break;
            }
            case "TThreadedSelectorServer":
            {
                TThreadedSelectorServer.Args thhsArgs = new TThreadedSelectorServer.Args((TNonblockingServerTransport) serverTransport);
                thhsArgs.processor(tprocessor);
                thhsArgs.transportFactory(getTTransportFactory());
                thhsArgs.protocolFactory(getTProtocolFactory());
                //半同步半异步的服务模型
                server = new TThreadedSelectorServer(thhsArgs);
                break;
            }
            default:
                throw new Exception("server model  is wrong");

        }
        return server;
    }

    public abstract  void init();
}
