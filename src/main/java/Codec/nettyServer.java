package Codec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class nettyServer {
    public static Logger logger_ = (Logger) LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    public static void main(String[] args) throws InterruptedException {
        /*
         *   两个线程租
         *   bossGroup只负责处理连接
         *   workerGroup处理业务需求
         *   两个线程都是轮询状态
         * */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动对象参数,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //链式进行设置
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioSctpChannel作为通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override//给piePeLine设置处理器
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给workerGroup 的 eventLoop设置对应的处理器
            logger_.info("is Ready");
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            //     端口绑定是异步操作
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        logger_.info("Listen 6668 success");
                    }else {
                        logger_.info("Listen 6668 fail");
                    }
                }
            });

            channelFuture.channel().closeFuture().sync();

        }finally {
                bossGroup.shutdownGracefully();
        }
    }

}
