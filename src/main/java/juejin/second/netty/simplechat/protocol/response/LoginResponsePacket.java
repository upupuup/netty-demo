package juejin.second.netty.simplechat.protocol.response;

import juejin.second.netty.simplechat.protocol.Packet;
import lombok.Data;

import static juejin.second.netty.simplechat.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
