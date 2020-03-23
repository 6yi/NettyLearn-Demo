package NettyGroupChat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GrouChatServerHandler extends SimpleChannelInboundHandler<String> {
    //全局的Channel,用于Channel广播
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd HH:mm:ss");
    //连接建立方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //广播方法
        channelGroup.writeAndFlush("[Client]@"+channel.remoteAddress()+"    Connection    "+dateFormat.format(new Date()));
        //加入Channel组
        channelGroup.add(channel);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[Client]@"+channel.remoteAddress()+".......Close.......");
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s)
            throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(h->{
            if(h!=channel){
                h.writeAndFlush("["+h.remoteAddress()+"]  :"+s+"\n");
            }
        });
    }
}
