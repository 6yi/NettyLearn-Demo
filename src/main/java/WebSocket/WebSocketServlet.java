package WebSocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServlet {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workGroup=new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //基于Http协议,使用http的编解码器
                        pipeline.addLast(new HttpServerCodec());
                        //以块方式写
                        pipeline.addLast(new ChunkedWriteHandler());
                        //数据聚合
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        /*
                        * 数据以帧传输,Frame
                        * 核心功能,将http协议转为ws协议(webSocket协议),保持长连接
                        * */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));

                        //自定义处理方法
                        pipeline.addLast(new MyTextWebSocketFrameHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.bind(7000).sync();

        channelFuture.channel().closeFuture().sync();

    }

}
