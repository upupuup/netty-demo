package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.JoinGroupRequestPacket;
import com.finaldemo.protocol.response.JoinGroupResponsePacket;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.experimental.var;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/20 20:42
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();
    
    public JoinGroupRequestHandler() {
        
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        // 1、获取群相应的channelGroup，然后将用户的channel添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());

        // 2、构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        ctx.writeAndFlush(responsePacket);
    }
}
