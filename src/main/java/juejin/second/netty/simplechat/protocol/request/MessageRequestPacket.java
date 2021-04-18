package juejin.second.netty.simplechat.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import juejin.second.netty.simplechat.protocol.Packet;

import static juejin.second.netty.simplechat.protocol.command.Command.MESSAGE_REQUEST;

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
