package tcp.protocoltcp;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;


public class MyserverHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private   int nums=0;



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        byte[] content = messageProtocol.getContent();
        int len = messageProtocol.getLen();
        System.out.println("收到数据:"+new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收数量:"+(++nums));


        String content2= UUID.randomUUID().toString();

        int responLen= content2.getBytes("utf-8").length;
        byte[] content2Bytes = content2.getBytes("utf-8");

        MessageProtocol messageProtocol2 = new MessageProtocol();
        messageProtocol2.setLen(responLen);
        messageProtocol2.setContent(content2Bytes);

        System.out.println("数据发送:"+responLen);
       channelHandlerContext.writeAndFlush(messageProtocol2);

    }
}
