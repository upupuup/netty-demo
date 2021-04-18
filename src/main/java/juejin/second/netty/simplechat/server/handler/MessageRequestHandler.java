package juejin.second.netty.simplechat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import juejin.second.netty.simplechat.protocol.request.MessageRequestPacket;
import juejin.second.netty.simplechat.protocol.response.MessageResponsePacket;
import juejin.second.netty.simplechat.session.Session;
import juejin.second.netty.simplechat.util.SessionUtil;

/*
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 16:15
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
		Session session = SessionUtil.getSession(ctx.channel());

		MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
		messageResponsePacket.setFromUserId(session.getUserId());
		messageResponsePacket.setFromUserName(session.getUserName());
		messageResponsePacket.setMessage(messageRequestPacket.getMessage());

		Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

		if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
			toUserChannel.writeAndFlush(messageResponsePacket);
			return;
		}

		System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
	}
}
