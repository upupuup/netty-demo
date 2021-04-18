package juejin.second.netty.simplechat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import juejin.second.netty.simplechat.protocol.PacketCodeC;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:48
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
	private final static int LENGTH_FIELD_OFFSET = 7;
	private final static int LENGTH_FIELD_LENGTH = 4;
	public Spliter() {
		super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		// 判断魔数是否符合要求
		if (buf.getInt(buf.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
			ctx.channel().close();
			return null;
		}
		return super.decode(ctx, buf);
	}
}
