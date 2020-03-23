package nettySimple.http;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
public class testHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest){
            System.out.println(httpObject.getClass());
            System.out.println("客户端地址:"+channelHandlerContext.channel().remoteAddress());
            //构造Http协议包
            ByteBuf content= Unpooled.copiedBuffer("hello,服务器在此", CharsetUtil.UTF_8);
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
                    , HttpResponseStatus.OK
                    , content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            //返回长度
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            channelHandlerContext.writeAndFlush(httpResponse);
        }
    }
}
