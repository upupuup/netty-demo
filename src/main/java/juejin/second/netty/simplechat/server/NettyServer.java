package juejin.second.netty.simplechat.server;

import juejin.second.netty.simplechat.codec.PacketEncoder;
import juejin.second.netty.simplechat.server.handler.AuthHandler;
import juejin.second.netty.simplechat.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import juejin.second.netty.simplechat.codec.PacketDecoder;
import juejin.second.netty.simplechat.codec.Spliter;
import juejin.second.netty.simplechat.server.handler.MessageRequestHandler;

import java.util.Date;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 15:39
 */
public class NettyServer {
	private static final int PORT = 8000;

	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		final ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(new PacketDecoder());
						ch.pipeline().addLast(new LoginRequestHandler());
						ch.pipeline().addLast(new AuthHandler());
						ch.pipeline().addLast(new MessageRequestHandler());
						ch.pipeline().addLast(new PacketEncoder());
					}
				});

		bind(serverBootstrap, PORT);
	}

	private static void bind(ServerBootstrap serverBootstrap, int port) {
		serverBootstrap.bind(port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
				return;
			}

			System.err.println("端口[" + port + "]绑定失败!");
		});
	}
}
