package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.GroupMessageRequestPacket;
import com.finaldemo.protocol.response.GroupMessageResponsePacket;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/20 21:00
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    public GroupMessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {
        // 1、拿到groupId构造群聊消息的响应
        String groupId = requestPacket.getToGroupId();
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMessage(requestPacket.getMessage());
        responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        // 2、拿到群聊对应的channelGroup，写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(responsePacket);
    }
}
