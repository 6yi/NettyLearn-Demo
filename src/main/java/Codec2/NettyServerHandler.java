package Codec2;

import Codec.StudentPojo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/*
*       自定义Handler
*
* */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDataInfo.MyMessage myMessage) throws Exception {

        MyDataInfo.MyMessage.DataType dataType=myMessage.getDataType();

        if (myMessage.getDataType() == MyDataInfo.MyMessage.DataType.studentType) {

            MyDataInfo.student stu = myMessage.getStu();
            System.out.println(stu.getId()+"  :   "+stu.getName());

        }else if(myMessage.getDataType() == MyDataInfo.MyMessage.DataType.workerType){

            MyDataInfo.worker worker = myMessage.getWk();
            System.out.println(worker.getAge()+"  :   "+worker.getName());

        }else{
            System.out.println("传输类型不正确");
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("连接成功",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }


}
