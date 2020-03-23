package Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {
    private String HOST = "127.0.0.1";
    private int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;
    public GroupChatClient() throws IOException {
        selector=Selector.open();
        socketChannel=SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName=socketChannel
                .getLocalAddress()
                .toString()
                .substring(1);
        System.out.println(userName+" : ok");
    }

    public void sednInfo(String info){
        try{
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try{
            int redChannel=selector.select();
            if (redChannel>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        SocketChannel socketChannel=(SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        System.out.println(new String(buffer.array()).trim());
                    }
                    iterator.remove();
                }

            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please set your dickName: ");
        String dickName=scanner.nextLine();


        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(){
            @Override
            public void run() {
                while(true){
                    groupChatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        }.start();

        while (scanner.hasNext()){
            String line=scanner.nextLine();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            groupChatClient.sednInfo(df.format(new Date())+" "+dickName+"  :  "+line);
        }
    }

}
