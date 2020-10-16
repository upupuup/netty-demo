package com.juejin.netty.ch2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Netty 客户端 demo
 * @Author:         jiangzhihong
 * @CreateDate:     2020/10/16 16:19
 */
public class NettyClient {
	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();

		bootstrap
				// 1.指定线程模型
				.group(group)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline().addLast(new StringEncoder());
						ch.pipeline().addLast(new FirstClientHandler());
						ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
							@Override
							protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
								ByteBuf byteBuf = (ByteBuf) msg;
								System.out.println(new Date() + "：客户端收到消息->" + byteBuf.toString(Charset.forName("utf-8")));
							}
						});
					}
				});

		// 4.建立连接
		Channel channel = bootstrap.connect(Constant.IP, Constant.PORT).channel();

		while (true) {
			channel.writeAndFlush(new Date() + "：Hello World");
			Thread.sleep(Constant.SLEEP_TWO_THOUSAND);
		}
	}
}
