package tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup work=new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try{
                bootstrap.group(boss,work)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG,128)
                        .option(ChannelOption.SO_KEEPALIVE,true)
                        .childHandler(new MyserverInitializer());
            ChannelFuture channelFuture = bootstrap.bind(7000).sync();

            channelFuture.channel().closeFuture().sync();

        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

}
