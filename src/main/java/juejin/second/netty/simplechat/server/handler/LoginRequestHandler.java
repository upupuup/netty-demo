package juejin.second.netty.simplechat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.second.netty.simplechat.protocol.request.LoginRequestPacket;
import juejin.second.netty.simplechat.protocol.response.LoginResponsePacket;
import juejin.second.netty.simplechat.session.Session;
import juejin.second.netty.simplechat.util.SessionUtil;

import java.util.Date;
import java.util.UUID;

/*
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 15:49
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
		LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		loginResponsePacket.setVersion(loginRequestPacket.getVersion());
		loginResponsePacket.setUserName(loginRequestPacket.getUserName());

		// 校验request的数据
		if (valid(loginRequestPacket)) {
			loginResponsePacket.setSuccess(true);
			String userId = randomUserId();
			loginResponsePacket.setUserId(userId);
			System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
			SessionUtil.bindSession(new Session(userId, loginResponsePacket.getUserName()), ctx.channel());
		} else {
			loginResponsePacket.setReason("校验密码失败");
			loginResponsePacket.setSuccess(false);
			System.out.println(new Date() + ": 登录失败!");
		}

		// 登录响应
		ctx.channel().writeAndFlush(loginResponsePacket);
	}

	private static String randomUserId() {
		return UUID.randomUUID().toString().split("-")[0];
	}

	private boolean valid(LoginRequestPacket loginRequestPacket) {
		return true;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		SessionUtil.unBindSession(ctx.channel());
	}
}
