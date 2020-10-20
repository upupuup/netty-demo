package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.QuitGroupRequestPacket;
import com.finaldemo.protocol.response.QuitGroupResponsePacket;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/20 20:49
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    public QuitGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {
        // 1、获取群对应的channelGroup，然后将当前用户的chnnel移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        // 2、构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(requestPacket.getGroupId());
        responsePacket.setSuccess(true);
        ctx.writeAndFlush(responsePacket);
    }
}
