package com.juejin.netty.ch2;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket服务端
 * @Author:         jiangzhihong
 * @CreateDate:     2020/10/16 10:55
 * Server 端首先创建了一个serverSocket来监听 8000 端口，然后创建一个线程，线程里面不断调用阻塞方法 serversocket.accept();
 * 获取新的连接，见(1)，当获取到新的连接之后，给每条连接创建一个新的线程，这个线程负责从该连接中读取数据，见(2)，
 * 然后读取数据是以字节流的方式，见(3)。
 */
public class IOServer {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(Constant.PORT);
		new Thread(() -> {
			while(true) {
				try {
					// （1）阻塞方法获取新连接
					Socket socket = serverSocket.accept();
					// （2）每一个连接都创建一个线程，负责读取数据
					new Thread(() -> {
						try {
							int len;
							byte[] data = new byte[1024];
							InputStream inputStream = socket.getInputStream();

							// （3）按流字节方式读取数据
							while ((len = inputStream.read(data)) != -1) {
								System.out.println(new String(data, 0, len));
							}
						} catch (IOException e) {

						}
					}).start();
				} catch (IOException e) {

				}
			}
		}).start();

	}
}
