package nettySimple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        nettyServer.logger_.info("client"+ctx);
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(Unpooled.copiedBuffer("from Client", CharsetUtil.UTF_8));
            }
        });


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        nettyServer.logger_.info("Server : "+buf.toString(CharsetUtil.UTF_8));
    }
}
