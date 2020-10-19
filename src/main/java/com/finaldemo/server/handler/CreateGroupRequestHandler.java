package com.finaldemo.server.handler;

import com.finaldemo.protocol.request.CreateGroupRequestPacket;
import com.finaldemo.protocol.response.CreateGroupResponsePacket;
import com.finaldemo.util.IDUtil;
import com.finaldemo.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: [java类作用描述]
 * @Author: jiangzhihong
 * @CreateDate: 2020/10/19 23:02
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    
    private CreateGroupRequestHandler() {
        
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        // 1、创建一个分组
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2、筛选出待加入的群聊的用户的channel和userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);

            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserId());
            }
        }

        // 3、创建群聊响应结果
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4、给每个客户端发送拉群消息
        channelGroup.writeAndFlush(createGroupResponsePacket);
        System.out.print("群创建成功，id 为 " + createGroupResponsePacket.getGroupId() + ", ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        // 5、保存群组相关的消息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
