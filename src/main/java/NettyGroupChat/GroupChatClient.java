package NettyGroupChat;

import Utils.Log;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {

    private String host;
    private int prot;

    public GroupChatClient(String host, int prot) {
        this.host = host;
        this.prot = prot;
    }

    public void run() throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("ClientHandler",new GroupChatClientHandler());
                        }
                    });
            Log.logger_.info("Connecting---------------");
            ChannelFuture channelFuture = bootstrap.connect(host,prot).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        Log.logger_.info("Successful!");
                    }else {
                        Log.logger_.info("Failed!");
                    }
                }
            });
            Scanner in=new Scanner(System.in);
            String msg;
            while (in.hasNext()){
                msg=in.nextLine();
                channelFuture.channel().writeAndFlush(msg+"\r\n");
            }

            channelFuture.channel().closeFuture().sync();

        }finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("59.110.173.180",8091   ).run();
    }
}
