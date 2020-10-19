package com.finaldemo.protocol.response;

import com.finaldemo.protocol.Packet;
import lombok.Data;
import static com.finaldemo.protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_RESPONSE;
    }
}
