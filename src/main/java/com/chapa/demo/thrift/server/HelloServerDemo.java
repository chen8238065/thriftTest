package com.chapa.demo.thrift.server;

import com.chapa.demo.thrift.auto.HelloWorldService;
import com.chapa.demo.thrift.server.common.ServerInit;
import com.chapa.demo.thrift.server.common.TServerFactory;
import com.chapa.demo.thrift.server.impl.HelloWorldImpl;
import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;

/**
 * Created by CHEN on 2016/3/7.
 * 单线程服务
 */
public class HelloServerDemo extends ServerInit{
    private static Logger logger = Logger.getLogger(HelloServerDemo.class);
    private TServerFactory serverFactory;

    public TServerFactory getServer() {
        return serverFactory;
    }

    public void setServer(TServerFactory server) {
        this.serverFactory = server;
    }

    public TServer getHelloServer() throws Exception {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");
            logger.info("Start...");
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                    new HelloWorldImpl());
            TServer server = serverFactory.getTServer(tprocessor);

            return server;
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
            throw  e;
        }
    }

    //代理 processor
    public void startServerV2() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");
            logger.info("Start...");
            TProcessor tprocessor = new ProcessorProxy(new HelloWorldService.Processor(
                    new HelloWorldImpl()));
            TServer server = getTServer(tprocessor);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * http://hanqunfeng.iteye.com/blog/1946999
     * http://www.cnblogs.com/cyfonly/p/6059374.html
     * http://blog.csdn.net/azhao_dn/article/details/8898610
     * @param args
     */
    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
    }

}
