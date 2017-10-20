package com.chapa.demo.thrift.server;

import org.apache.thrift.server.TServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by chapa on 17-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-thrift.xml"})
public class HelloServerTest {

    @Resource
    HelloServerDemo helloServerDemo;

    @Test
    public void startServer() throws Exception {
        TServer server=helloServerDemo.getHelloServer();
        //事件监听机制 来获取 client 信息
        server.setServerEventHandler(new MyTServerEventHandler());
        server.serve();
    }

}