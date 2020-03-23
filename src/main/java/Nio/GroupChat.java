package Nio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChat {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    public GroupChat(){
        try{
            this.selector=Selector.open();
            listenChannel=ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void listen(){
        try{
            while (true){
                int count=selector.select(1000);
                if (count>0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress()+"------>:Connection Successful");
                        }else if (key.isReadable()){
                                readData(key);
                        }
                       iterator.remove();
                    }

                }else {
//                    System.out.println("Wait....");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    private void sendInfoToOtherClient(String msg,SocketChannel self) {
        // 广播
        selector.keys().forEach(key->{
            Channel targetChannel=key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel!=self){
                SocketChannel dstChannel=(SocketChannel)targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    dstChannel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("-------Fanout  Successful-----");
    }

    private void readData(SelectionKey key){
            SocketChannel socketChannel = null;
            try{
                socketChannel=(SocketChannel)key.channel();
                ByteBuffer buffer =ByteBuffer.allocate(1024);
                StringBuffer stringBuffer=new StringBuffer();
                int len=socketChannel.read(buffer);
                stringBuffer.append(new String(buffer.array(),0,len));
                System.out.println(socketChannel.getRemoteAddress()+" : Msg Successful");
                sendInfoToOtherClient(stringBuffer.toString(),socketChannel);
            }catch (IOException e){
                try {
                    sendInfoToOtherClient(socketChannel.getRemoteAddress()+"------>:Closed",socketChannel);
                    //取消注册
                    key.cancel();
                    socketChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }

    public static void main(String[] args) {
        GroupChat groupChat = new GroupChat();
        groupChat.listen();
    }

}
