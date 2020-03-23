package nettySimple;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class tcpIp {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket=null;
        while (true) {
            socket=serverSocket.accept();
            Socket finalSocket = socket;
            new Thread(){
                @Override
                public void run() {
                    try {
                        OutputStream out=finalSocket.getOutputStream();
                        StringBuffer result=new StringBuffer();
                        result.append("HTTP /1.1 200 ok \r\n");
                        result.append("Content-Type:text/plain;charset=utf-8\r\n");
                        result.append("\r\n");
                        result.append("来自服务端http的消息");
                        System.out.println(result.toString());
                        out.write(result.toString().getBytes());
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }


    }

}
