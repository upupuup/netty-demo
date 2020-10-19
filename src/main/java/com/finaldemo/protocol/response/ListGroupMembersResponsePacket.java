package com.finaldemo.protocol.response;

import com.finaldemo.protocol.Packet;
import com.finaldemo.session.Session;
import lombok.Data;
import java.util.List;
import static com.finaldemo.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
