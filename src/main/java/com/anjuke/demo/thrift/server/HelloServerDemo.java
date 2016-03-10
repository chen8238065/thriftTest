package com.anjuke.demo.thrift.server;

import com.anjuke.demo.thrift.auto.HelloWorldService;
import com.anjuke.demo.thrift.server.common.ServerInit;
import com.anjuke.demo.thrift.server.impl.HelloWorldImpl;
import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;

import java.lang.reflect.Type;
/**
 * Created by CHEN on 2016/3/7.
 * 单线程服务
 */
public class HelloServerDemo extends ServerInit{
    private static Logger logger = Logger.getLogger(HelloServerDemo.class);
    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");
            logger.info("Start...");
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                    new HelloWorldImpl());
            TServer server = getTServer(tprocessor);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer();
    }

}
