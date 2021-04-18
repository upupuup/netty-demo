package juejin.second.netty.simplechat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import juejin.second.netty.simplechat.protocol.PacketCodeC;

import java.util.List;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:43
 */
public class PacketDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> outList) throws Exception {
		outList.add(PacketCodeC.INSTANCE.decode(in));
	}
}
