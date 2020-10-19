package com.finaldemo.protocol.request;

import com.finaldemo.protocol.Packet;
import lombok.Data;
import static com.finaldemo.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
