package nettySimple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Callable;

/*
*       自定义Handler
*
* */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    static final EventExecutorGroup group= new DefaultEventExecutorGroup(16);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        group.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                nettyServer.logger_.info("server ctx:"+ctx);
                ByteBuf byteBuf= (ByteBuf) msg;
                nettyServer.logger_.info("msg : "+byteBuf.toString(CharsetUtil.UTF_8));
                nettyServer.logger_.info(ctx.channel().remoteAddress());
                return null;
            }
        });

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.writeAndFlush(Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

}
