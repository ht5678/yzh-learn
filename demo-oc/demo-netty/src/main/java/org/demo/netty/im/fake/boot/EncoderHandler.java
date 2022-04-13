package org.demo.netty.im.fake.boot;


import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.util.PacketEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author yue
 *
 */
public class EncoderHandler extends MessageToByteEncoder<Packet>{

	private PacketEncoder encoder;
	
	
	public EncoderHandler(PacketEncoder encoder){
		this.encoder = encoder;
	}
	
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		byte[] bytes = encoder.encodePacket(packet);
		
		int len = bytes.length;
		out.writeInt(len);
		out.writeBytes(bytes);
	}

}
