package Handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyclientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("MyclientHandler发送数据");
//        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer());
        channelHandlerContext.writeAndFlush(123456L);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyclientHandler发送数据");
//        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer());
       ctx.writeAndFlush(123456L);
    }
}
