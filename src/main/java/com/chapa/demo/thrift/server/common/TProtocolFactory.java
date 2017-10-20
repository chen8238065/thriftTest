package com.chapa.demo.thrift.server.common;

import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by chapa on 17-10-13.
 */
public class TProtocolFactory implements FactoryBean {

    private TProtocolFactory.TProtocolType protocolType;
    private TTransport transport;

    public TProtocolFactory.TProtocolType  getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(TProtocolFactory.TProtocolType  protocolType) {
        this.protocolType = protocolType;
    }

    public TTransport getTransport() {
        return transport;
    }

    public void setTransport(TTransport transport) {
        this.transport = transport;
    }

    @Override
    public TProtocol getObject() throws Exception {
        TProtocol protocol=null;
        switch (protocolType){
            case TBinaryProtocol://二进制格式
                protocol=new TBinaryProtocol(transport);
                break;
            case TCompactProtocol://压缩格式
                protocol=new TCompactProtocol(transport);
                break;
            case TJSONProtocol://JSON格式
                protocol=new TJSONProtocol(transport);
                break;
            case TSimpleJSONProtocol://提供JSON只写协议, 生成的文件很容易通过脚本语言解析
                protocol=new TSimpleJSONProtocol(transport);
                break;
            case TTupleProtocol://继承于TCompactProtocol，Struct的编解码时使用更省地方
                protocol=new TTupleProtocol(transport);
                break;
            default:
                throw new Exception("protocol is wrong");

        }
        return protocol;
    }
    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Class<?> getObjectType() {
        return TProtocol.class;
    }


    public  enum TProtocolType{
        TBinaryProtocol,
        TCompactProtocol,
        TJSONProtocol,
        TSimpleJSONProtocol,
        TTupleProtocol
    }
}
