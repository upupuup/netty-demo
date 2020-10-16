package com.juejin.netty.ch2;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Socket客户端
 * @Author:         jiangzhihong
 * @CreateDate:     2020/10/16 11:03
 */
public class IOClient {
	public static void main(String[] args) {

		new Thread(() -> {
			try {
				Socket socket = new Socket(Constant.IP, Constant.PORT);
				while (true) {
					try {
						socket.getOutputStream().write((new Date() + ":Hello World").getBytes());
						Thread.sleep(2000);
					} catch (Exception e) {

					}
				}
			} catch (IOException e) {

			}
		}).start();
	}
}
