package juejin.second.netty.simplechat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import juejin.second.netty.simplechat.protocol.Packet;
import juejin.second.netty.simplechat.protocol.PacketCodeC;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:46
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) throws Exception {
		PacketCodeC.INSTANCE.encode(byteBuf, packet);
	}
}
