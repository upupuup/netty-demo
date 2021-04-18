package juejin.second.netty.simplechat.client;

import juejin.second.netty.simplechat.client.handler.LoginResponseHandler;
import juejin.second.netty.simplechat.client.handler.MessageResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import juejin.second.netty.simplechat.codec.PacketDecoder;
import juejin.second.netty.simplechat.codec.PacketEncoder;
import juejin.second.netty.simplechat.codec.Spliter;
import juejin.second.netty.simplechat.protocol.request.LoginRequestPacket;
import juejin.second.netty.simplechat.protocol.request.MessageRequestPacket;
import juejin.second.netty.simplechat.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:58
 */
public class NettyClient {
	private static final int MAX_RETRY = 5;
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8000;

	public static void main(String[] args) {
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(new PacketDecoder());
						ch.pipeline().addLast(new LoginResponseHandler());
						ch.pipeline().addLast(new MessageResponseHandler());
						ch.pipeline().addLast(new PacketEncoder());
					}
				});
		connect(bootstrap, HOST, PORT, MAX_RETRY);
	}

	private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
		bootstrap.connect(host, port).addListener(future -> {
			// 判断是否成功
			if (future.isSuccess()) {
				System.out.println(new Date() + ": 连接成功，启动控制台线程……");
				Channel channel = ((ChannelFuture) future).channel();
				startConsoleThread(channel);
				return;
			}

			// 判断重试次数
			if (retry == 0) {
				System.err.println("重试次数已用完，放弃连接！");
				return;
			}

			int order = MAX_RETRY - retry + 1;
			int delay = 1 << order;

			System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

			bootstrap.config().group().schedule(() ->
				connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS
			);
		});
	}

	private static void startConsoleThread(Channel channel) {
		Scanner sc = new Scanner(System.in);
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		new Thread(() -> {
			while (!Thread.interrupted()) {
				if (!SessionUtil.hasLogin(channel)) {
					System.out.print("输入用户名登录：");
					String username = sc.nextLine();
			 		loginRequestPacket.setUserName(username);
					loginRequestPacket.setPassword("pwd");

					// 发送数据包
					channel.writeAndFlush(loginRequestPacket);
					waitForLoginResponse();
				} else {
					String toUserId = sc.next();
					String message = sc.next();
					channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
				}
			}
		}).start();
	}

	private static void waitForLoginResponse() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
