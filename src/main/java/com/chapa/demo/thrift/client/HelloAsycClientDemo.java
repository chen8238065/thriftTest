package com.chapa.demo.thrift.client;

import com.chapa.demo.thrift.auto.HelloWorldService;
import com.chapa.demo.thrift.auto.HelloWorldService.AsyncClient.sayHello_call;
import com.chapa.demo.thrift.client.common.ClientInit;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingTransport;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * Created by CHEN on 2016/3/8.
 */
public class HelloAsycClientDemo extends ClientInit{
    private static Logger logger = Logger.getLogger(HelloAsycClientDemo.class);
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HelloAsycClientDemo.class);
    public void startClient(String userName) {
        TNonblockingTransport transport=null;
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            transport = (TNonblockingTransport) getClientTTransport();

            TProtocolFactory tprotocol = getTProtocolFactory();
            HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(
                    tprotocol, clientManager, transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
            System.out.println("call method sayHello start ...");
            asyncClient.sayHello(userName, callBack);
            System.out.println("call method sayHello .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            transport.close();
        }
        System.out.println("startClient end.");
    }

    public class AsynCallback implements AsyncMethodCallback<sayHello_call> {
        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(sayHello_call response) {
            System.out.println("onComplete");
            try {
                // Thread.sleep(1000L * 1);
                System.out.println("AsynCall result =:"
                        + response.getResult().toString());
            } catch (TException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloAsycClientDemo client = new HelloAsycClientDemo();
        client.startClient("Michael");
//        client.startClient("Michael");
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
