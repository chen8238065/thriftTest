package com.chapa.demo.thrift.server.impl;

import com.chapa.demo.thrift.auto.HelloWorldService;
import org.apache.thrift.TException;

/**
 * Created by CHEN on 2016/3/7.
 */
public class HelloWorldImpl implements HelloWorldService.Iface{

    public String sayHello(String username) throws TException {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hi," + username;
    }
}
