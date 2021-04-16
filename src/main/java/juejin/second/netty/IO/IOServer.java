package juejin.second.netty.IO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static juejin.second.netty.constant.Constant.PORT;

/**
 * @Description: 传统的IO编程中客户端实现
 * @Author: jiangzhihong
 * @CreateDate: 2021/4/16 21:02
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        // （1）接收新连接的线程
        new Thread(()->{
            while (true) {
                try {
                    //（1）阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    // 给每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        System.out.println(Thread.currentThread().getName() + "====" + Math.random());
                            try {
                                int len;
                                byte[] data = new byte[1024];
                                InputStream inputStream = socket.getInputStream();
                                // 按字节流的方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println("收到消息：" + new String(data, 0, len));
                                }
                            } catch (IOException e) {
                                e.getMessage();
                            }
                        }
                    ).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
