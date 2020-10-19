package com.finaldemo.protocol.request;

import com.finaldemo.protocol.Packet;
import lombok.Data;
import static com.finaldemo.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
