package Simpledubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClienrtHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext Context;
    private String result;
    private String params;



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("??");
        Context=ctx;

    }


    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       result= ((String) msg);
       notify(); //唤醒等待线程
    }


    @Override
    public synchronized Object call() throws Exception {
        Context.writeAndFlush(params);
        wait();
        return result;
    }

    void setParams(String params){
        this.params=params;
    }

}
