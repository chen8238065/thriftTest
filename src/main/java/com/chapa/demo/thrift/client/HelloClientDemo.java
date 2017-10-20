package com.chapa.demo.thrift.client;

import com.chapa.demo.thrift.auto.HelloWorldService;
import com.chapa.demo.thrift.client.common.ClientInit;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by CHEN on 2016/3/7.
 */
public class HelloClientDemo {

    private TTransport transport;

    private TProtocol tProtocol;

    public TTransport getTransport() {
        return transport;
    }

    public void setTransport(TTransport transport) {
        this.transport = transport;
    }

    public TProtocol gettProtocol() {
        return tProtocol;
    }

    public void settProtocol(TProtocol tProtocol) {
        this.tProtocol = tProtocol;
    }


    public HelloWorldService.Client getClient() throws Exception {
        TTransport transport = getTransport();
        try {
            HelloWorldService.Client client = new HelloWorldService.Client(
                    tProtocol);
            transport.open();
            return client;
        } catch (Exception e) {
            throw  e;
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }


    public void startHttpClient(String userName) {
        THttpClient transport= (THttpClient) getTransport();
        try {
            HelloWorldService.Client client = new HelloWorldService.Client(
                    tProtocol);
            transport.open();
            transport.setConnectTimeout(1000);
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
        client.startHttpClient("Michael");
//        client.startClient("Mich单打独斗ael");

    }

}