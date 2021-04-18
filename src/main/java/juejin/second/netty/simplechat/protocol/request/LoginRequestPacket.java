package juejin.second.netty.simplechat.protocol.request;

import juejin.second.netty.simplechat.protocol.Packet;
import lombok.Data;

import static juejin.second.netty.simplechat.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
