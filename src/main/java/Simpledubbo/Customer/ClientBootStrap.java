package Simpledubbo.Customer;

import Simpledubbo.netty.NettyClient;
import Simpledubbo.publicInterface.HelloServer;

public class ClientBootStrap {
    public static final String providerName="HelloServer#hello#";

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        HelloServer bean = (HelloServer) nettyClient.getBean(HelloServer.class, providerName);
        String s = bean.Hello("dubbo rpc调用");
        System.out.println(s);

    }
}
