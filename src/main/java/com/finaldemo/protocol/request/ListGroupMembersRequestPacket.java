package com.finaldemo.protocol.request;

import com.finaldemo.protocol.Packet;
import lombok.Data;

import static com.finaldemo.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
