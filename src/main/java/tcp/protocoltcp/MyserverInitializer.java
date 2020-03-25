package tcp.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyserverInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addLast(new MyMessageDecoder());
        socketChannel.pipeline().addLast(new MyMessageEncoder());
        socketChannel.pipeline().addLast(new MyserverHandler());
    }
}
