package org.demo.netty.im.bs.message;

import org.demo.netty.domain.Packet;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:53:54
 */
public class PongPacketMessage implements Message{

	private final Packet packet;
	
	public PongPacketMessage(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}
}