package juejin.second.netty.simplechat.protocol.response;

import juejin.second.netty.simplechat.protocol.Packet;
import lombok.Data;

import static juejin.second.netty.simplechat.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
