package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.LoginRequestPacket;
import com.finaldemo.protocol.response.LoginResponsePacket;
import com.finaldemo.session.Session;
import com.finaldemo.util.IDUtil;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/19 21:52
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("lallalalalalal");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
        }

        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
