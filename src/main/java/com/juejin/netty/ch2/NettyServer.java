package com.juejin.netty.ch2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2020/10/16 16:11
 */
public class NettyServer {
	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		/**
		 * Netty服务端，必须要指定三类属性，线程模型、IO 模型、连接读写处理逻辑
		 * ServerBootstrap这个类将引导我们进行服务端的启动工作
		 * group(bossGroup, workerGroup)给引导类配置两大线程组，这个引导类的线程模型也就定型了
		 * channel(NioServerSocketChannel.class)来指定 IO 模型
		 * initChannel：定义后续每条连接的数据读写，业务处理逻辑
		 * NioServerSocketChannel和NioSocketChannel的概念可以和 BIO 编程模型中的ServerSocket以及Socket两个概念对应上
		 */
		serverBootstrap
				.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>(){
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
							@Override
							protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
								System.out.println(msg);

								System.out.println(new Date() + "：服务端回复数据");
								ByteBuf out = this.getByteBuf(ctx);
								ctx.channel().writeAndFlush(out);
							}

							private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
								byte[] bytes = "Hello, nice to meet you".getBytes(Charset.forName("utf-8"));
								ByteBuf buffer = ctx.alloc().buffer();
								ByteBuf byteBuf = buffer.writeBytes(bytes);
								return byteBuf;
							}
						});
					}
				}).bind(Constant.PORT);
		System.out.println("启动netty server");

	}
}
