package com.finaldemo.server;

import com.finaldemo.codec.PacketCodecHandler;
import com.finaldemo.codec.Spliter;
import com.finaldemo.server.handler.AuthHandler;
import com.finaldemo.server.handler.IMHandler;
import com.finaldemo.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

/**
 * @Description: 服务端
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/19 21:39
 */
public class NettyServer {
    private static final int PORT = 8001;

    public static void main(String[] args) {
        NioEventLoopGroup boosGreop = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGreop, workerGroup)
                .channel(NioServerSocketChannel.class)
                // ?
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
           if (future.isSuccess()) {
               System.out.println(new Date() + "：端口[" + port + "]绑定成功");
           } else {
               System.err.println("端口[" + port + "]绑定失败");
           }
        });
    }
}
