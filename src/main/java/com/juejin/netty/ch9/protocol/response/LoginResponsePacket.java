package com.juejin.netty.ch9.protocol.response;

import com.juejin.netty.ch9.protocol.Packet;
import lombok.Data;

import static com.juejin.netty.ch9.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
