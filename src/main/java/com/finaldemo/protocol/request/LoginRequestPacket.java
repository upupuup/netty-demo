package com.finaldemo.protocol.request;

import com.finaldemo.protocol.Packet;
import lombok.Data;

import static com.finaldemo.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
