package tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyserverHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private   int nums=0;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        byte[] buf=new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(buf);
        System.out.println("服务器接收到的数据: "+new String(buf, CharsetUtil.UTF_8));
        System.out.println("服务器接收的消息量:"+(++nums));
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString(),CharsetUtil.UTF_8));

    }
}
