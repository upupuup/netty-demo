package com.juejin.netty.ch2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2020/10/16 17:25
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println(new Date() + "：客户端写出数据");
		// 1、获取数据
		ByteBuf buffer = this.getByteBuf(ctx);
		// 2、写数据
		ctx.channel().writeAndFlush(buffer);
	}

	private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
		// 1、获取二进制抽象 ByteBuf
		ByteBuf buffer = ctx.alloc().buffer();
		// 2、准备数据，指定字符串为utf-8
		byte[] bytes = Constant.SAY_HELLO.getBytes(Charset.forName("utf-8"));
		// 3、填充数据到ByteBuf
		buffer.writeBytes(bytes);

		return buffer;
	}
}
