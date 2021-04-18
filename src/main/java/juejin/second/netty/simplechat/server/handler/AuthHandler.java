package juejin.second.netty.simplechat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import juejin.second.netty.simplechat.util.SessionUtil;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 16:08
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!SessionUtil.hasLogin(ctx.channel())) {
			ctx.channel().closeFuture();
			return;
		}

		ctx.pipeline().remove(this);
		super.channelRead(ctx, msg);
	}
}
