package juejin.second.netty.IO;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import static juejin.second.netty.constant.Constant.IP;
import static juejin.second.netty.constant.Constant.PORT;

/**
 * @Description: 传统的IO编程中客户端实现
 * @Author: jiangzhihong
 * @CreateDate: 2021/4/16 21:09
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() ->{
                while (true) {
                    try {
                        Socket socket = new Socket(IP, PORT);
                        String msg = new Date() + ":hello world";
                        socket.getOutputStream().write(msg.getBytes());
                        System.out.println("发送消息：" + msg);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        ).start();
    }
}
