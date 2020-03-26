package Simpledubbo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClienrtHandler client;

    //代理模式
    public Object getBean(final Class<?> service,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                ,new Class<?>[]{service}
                ,(proxy, method, args) ->{
                    if (client==null){
                            initClient();
                    }
                    System.out.println("代理方法被调用");
                    client.setParams(providerName+args[0]);
                    return executorService.submit(client).get();
                });
    }


    private static void initClient() throws InterruptedException {
        client=new NettyClienrtHandler();
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
//        System.out.println("卡这了哥们,牢记啊,别瞎几把用sync,会阻塞的");
//        channelFuture.channel().closeFuture().sync();
    }

}
