package Codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int i = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage=null;
        if(i==0){
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.studentType)
                    .setStu(MyDataInfo.student.newBuilder().setName("stu").setId(123).build()).build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.workerType)
                    .setWk(MyDataInfo.worker.newBuilder().setName("worker").setAge(789).build()).build();
        }
        ctx.writeAndFlush(myMessage);
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        nettyServer.logger_.info("Server : "+buf.toString(CharsetUtil.UTF_8));
    }
}
