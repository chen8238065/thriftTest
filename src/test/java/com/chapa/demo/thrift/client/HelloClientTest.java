package com.chapa.demo.thrift.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by chapa on 17-10-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-thrift.xml"})
public class HelloClientTest {

    @Resource(name="helloClientDemo")
    HelloClientDemo helloClientDemo;


    //region helloClientDemo
    /**
     * you must run HelloServerTest.startSimpleServer for these tests
     */

    /**
     * 必须先 open transport 否则异常
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testClient$binaryProtocol$TSocket1() throws Exception {
        HelloWorldService.Client client = new HelloWorldService.Client(
                helloClientDemo.gettProtocol());
        System.out.println(client.sayHello("chapa123"));

    }

    /**
     *  close transport 后,不能再请求
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testClient$binaryProtocol$TSocket2() throws Exception {
        HelloWorldService.Client client = new HelloWorldService.Client(
                helloClientDemo.gettProtocol());
        helloClientDemo.getTransport().open();
        System.out.println(client.sayHello("chapa123"));
        helloClientDemo.getTransport().close();
        System.out.println(client.sayHello("chapa456"));

    }

    /**
     * sudo tshark -i  lo -f 'port 8888'
     * 一次链接 多次 请求 (长连接)
     * @throws Exception
     */
    @Test
    public void testClient$binaryProtocol$TSocket() throws Exception {
        HelloWorldService.Client client = new HelloWorldService.Client(
                helloClientDemo.gettProtocol());
        helloClientDemo.getTransport().open();
        System.out.println(client.sayHello("chapa"));
        System.out.println(client.sayHello("chapa123"));
        helloClientDemo.getTransport().close();
    }
    //endregion



    @Test
    public void startHttpClient() throws Exception {
        for ( int i = 0; i < 1; i++) {
            final String str= "$"+i;
            new Thread(){
                @Override
                public void run() {
                    helloClientDemo.startHttpClient("Michael"+str);
                }
            }.start();
        }
        Thread.sleep(1000);

    }

}