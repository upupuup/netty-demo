package com.finaldemo.protocol.response;

import com.finaldemo.protocol.Packet;
import com.finaldemo.session.Session;
import lombok.Data;
import static com.finaldemo.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
