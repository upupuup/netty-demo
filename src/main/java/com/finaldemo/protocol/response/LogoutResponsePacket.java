package com.finaldemo.protocol.response;

import com.finaldemo.protocol.Packet;
import lombok.Data;

import static com.finaldemo.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
