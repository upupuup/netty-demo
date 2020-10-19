package com.finaldemo.protocol.request;

import com.finaldemo.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.finaldemo.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
