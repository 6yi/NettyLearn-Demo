package Simpledubbo.Provider;

import Simpledubbo.publicInterface.HelloServer;

public class HelloServerImpl implements HelloServer {
    @Override
    public String Hello(String msg) {
        System.out.println("收到客户端消息:"+msg);
        if (msg!=null){
            return "收到消息:"+msg;
        }else{
            return "收到消息:";
        }

    }
}
