package Codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPojo.Student student = StudentPojo.Student.newBuilder().setId(67).setNam("刘正").build();
        ctx.writeAndFlush(student);
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        nettyServer.logger_.info("Server : "+buf.toString(CharsetUtil.UTF_8));
    }
}
