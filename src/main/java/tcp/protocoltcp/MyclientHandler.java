package tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyclientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int nums;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <5; i++) {
            String mes="刘正牛逼呀,喜喜";
            byte[] bytes = mes.getBytes(CharsetUtil.UTF_8);
            int length=mes.getBytes().length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol)
            throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        System.out.println(new String(content,CharsetUtil.UTF_8));
        System.out.println("长度为:"+len);

    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
////        byte[] buf=new byte[byteBuf.readableBytes()];
////        byteBuf.readBytes(buf);
////        System.out.println(new String(buf,CharsetUtil.UTF_8));
////        System.out.println("接收次数:"+(++nums));
////    }


}
