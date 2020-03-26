package Simpledubbo.Provider;

import Simpledubbo.netty.NettyServer;

public class ServerBootStarp {

    public static void main(String[] args) throws InterruptedException {
        NettyServer.startServer("127.0.0.1", 7000);


    }
}
