package com.anjuke.demo.thrift.client;

import com.anjuke.demo.thrift.auto.HelloWorldService;
import com.anjuke.demo.thrift.client.common.ClientInit;
import org.apache.thrift.transport.TTransport;

/**
 * Created by CHEN on 2016/3/7.
 */
public class HelloClientDemo extends ClientInit {
    /**
     *
     * @param userName
     */
    public void startClient(String userName) {
        TTransport transport = getClientTTransport();
        try {
            HelloWorldService.Client client = new HelloWorldService.Client(
                    getTProtocol(transport));
            transport.open();
            String result = client.sayHello(userName);
            System.out.println("Thrift client result =: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloClientDemo client = new HelloClientDemo();
        client.startClient("Michael");

    }

}