package com.imooc.netty.ch2;

/**
 * @author 闪电侠
 */
public class ServerBoot {

    private static final int PORT = 8001;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }

}
