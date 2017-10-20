package com.chapa.demo.thrift.server;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

/**
 * Created by chapa on 17-10-11.
 */
public class ProcessorProxy implements TProcessor {

    private TProcessor processor;

    public ProcessorProxy(TProcessor processor)
    {
        this.processor = processor;
    }

    /**
     * 该方法，客户端每调用一次，就会触发一次
     */
    @Override
    public boolean process(TProtocol in, TProtocol out) throws TException
    {
        /**
         * 从TProtocol里面获取TTransport对象
         * 把TTransport对象转换成TSocket，然后在TSocket里面获取Socket，就可以拿到客户端IP
         */
        TSocket socket = (TSocket)in.getTransport();
        System.out.println(socket.getSocket().getRemoteSocketAddress());
        return processor.process(in, out);
    }
}
