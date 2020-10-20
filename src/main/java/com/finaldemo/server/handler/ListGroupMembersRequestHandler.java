package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.ListGroupMembersRequestPacket;
import com.finaldemo.protocol.response.ListGroupMembersResponsePacket;
import com.finaldemo.session.Session;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/20 20:54
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    public ListGroupMembersRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) throws Exception {
        // 1、获取群的ChannelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2、遍历群成员的channel，对应的session，构造群成员信息
        List<Session> sessionList = new ArrayList<>();
        channelGroup.stream().forEach(channel -> {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        });

        // 3、构建获取成员列表响应写回客户端
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.writeAndFlush(responsePacket);
    }
}
