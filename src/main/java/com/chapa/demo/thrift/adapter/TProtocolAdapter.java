package com.chapa.demo.thrift.adapter;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

/**
 * Created by CHEN on 2016/3/8.
 */
public abstract class TProtocolAdapter extends TProtocol{

    protected TProtocolAdapter(TTransport trans) {
        super(trans);
    }
}
