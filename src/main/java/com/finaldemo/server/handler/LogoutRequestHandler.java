package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.LogoutRequestPacket;
import com.finaldemo.protocol.response.LogoutResponsePacket;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/20 21:05
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    public LogoutRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket requestPacket) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.writeAndFlush(logoutResponsePacket);
    }
}
