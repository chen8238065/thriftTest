package com.chapa.demo.thrift.server;

import com.chapa.demo.thrift.auto.HelloWorldService;
import com.chapa.demo.thrift.server.impl.HelloWorldImpl;
import io.netty.buffer.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chapa on 17-10-12.
 */
public class ThriftNettyHandlerProxy extends ChannelInboundHandlerAdapter {
    public static Map pathIFaceMap=new HashMap<String,Class>();
    HttpVersion HTTP_1_1 = new HttpVersion("HTTP/1.0",true);
    HttpResponseStatus OK = new HttpResponseStatus(200, "OK");
    static{
        //pathIFaceMap.put("helloTest",);
    }
    HttpRequest request=null;
    private int count=0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            request.headers();
            String uri = request.getUri();
//            System.out.println("Uri:" + uri);
            System.out.println(count++);
        }
        if (msg instanceof HttpContent) {
            System.out.println(count++);
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            ctx.write(process(new ByteBufInputStream(buf)));
            ctx.flush();
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
    public DefaultFullHttpResponse process(ByteBufInputStream buf) {
        TTransport inTransport = null;
        TTransport outTransport = null;
        TProtocolFactory inProtocolFactory=new TJSONProtocol.Factory();
        TProtocolFactory outProtocolFactory=new TJSONProtocol.Factory();
        DefaultFullHttpResponse response = null;
        try {
            ByteBufOutputStream out =new ByteBufOutputStream(new CompositeByteBuf(new UnpooledByteBufAllocator(true),true,Integer.MAX_VALUE));
            TTransport transport = new TIOStreamTransport(buf, out);
            inTransport = transport;
            outTransport = transport;

            TProtocol inProtocol = inProtocolFactory.getProtocol(inTransport);
            TProtocol outProtocol = outProtocolFactory
                    .getProtocol(outTransport);
            TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                    new HelloWorldImpl());

            processor.process(inProtocol, outProtocol);
            response= new DefaultFullHttpResponse(HTTP_1_1, OK,out.buffer());
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/x-thrift");
        } catch (TException te) {
        }
        return response;
    }

}
