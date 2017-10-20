package com.chapa.demo.thrift.server.common;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by chapa on 17-10-13.
 */
public class TProtocolFactoryFactory implements FactoryBean {
    private TProtocolFactory.TProtocolType protocolType;

    public TProtocolFactory.TProtocolType  getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(TProtocolFactory.TProtocolType  protocolType) {
        this.protocolType = protocolType;
    }

    @Override
    public org.apache.thrift.protocol.TProtocolFactory getObject() throws Exception {
        org.apache.thrift.protocol.TProtocolFactory fac=null;
        switch (getProtocolType()){
            case TBinaryProtocol://二进制格式
                fac=new TBinaryProtocol.Factory();
                break;
            case TCompactProtocol://压缩格式
                fac=new TCompactProtocol.Factory();
                break;
            case TJSONProtocol://JSON格式
                fac=new TJSONProtocol.Factory();
                break;
            case TSimpleJSONProtocol://提供JSON只写协议, 生成的文件很容易通过脚本语言解析
                fac=new TSimpleJSONProtocol.Factory();
                break;
            default:
                throw new Exception("protocol factory is wrong");

        }
        return fac;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<?> getObjectType() {
        return org.apache.thrift.protocol.TProtocolFactory.class;
    }


}
