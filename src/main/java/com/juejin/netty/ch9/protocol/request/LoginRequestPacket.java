package com.juejin.netty.ch9.protocol.request;

import com.juejin.netty.ch9.protocol.Packet;
import lombok.Data;
import static com.juejin.netty.ch9.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
