package com.chapa.demo.thrift.client;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chapa on 17-10-12.
 */
public class HelloAsycClientTest {
    HelloAsycClientDemo client = new HelloAsycClientDemo();
    @Test
    public void startClient() throws Exception {
        client.startClient("chapa");
    }

}