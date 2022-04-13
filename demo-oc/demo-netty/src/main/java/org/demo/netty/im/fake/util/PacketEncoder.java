package org.demo.netty.im.fake.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.demo.netty.im.fake.domain.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;

/**
 * 
 * @author yue
 *
 */
public class PacketEncoder {
	
	private final boolean isPreferDirectBuffer;
	private final JsonSupport jsonSupport;

	public PacketEncoder(boolean isPreferDirectBuffer, JsonSupport jsonSupport) {
		this.isPreferDirectBuffer = isPreferDirectBuffer;
		this.jsonSupport = jsonSupport;
	}
	
	public JsonSupport getJsonSupport() {
		return jsonSupport;
	}

	public ByteBuf allocateBuffer(ByteBufAllocator allocator) {
		if (isPreferDirectBuffer) {
			return allocator.ioBuffer();
		}
		return allocator.heapBuffer();
	}
	
	public void encodePackets(Queue<Packet> packets, ByteBuf buffer, ByteBufAllocator allocator, int limit) throws IOException {
		 
		List<Packet> result = new ArrayList<>();
		while (true) {
			Packet packet = packets.poll();
			if (packet == null) {
				break;
			}
			result.add(packet);
		}
		if (!result.isEmpty()) {
			ByteBuf encBuf = allocateBuffer(allocator);
			ByteBufOutputStream out = new ByteBufOutputStream(encBuf);
			jsonSupport.write(out, result);
			if (encBuf != null) {
				buffer.writeBytes(encBuf);
				encBuf.release();
			}
			out.close();
		}
	}
	
	public void encodePacket(Packet packet, ByteBuf buffer, ByteBufAllocator allocator, int limit) throws IOException {
		ByteBuf encBuf = allocateBuffer(allocator);
		ByteBufOutputStream out = new ByteBufOutputStream(encBuf);
		jsonSupport.write(out, packet);
		if (encBuf != null) {
			buffer.writeBytes(encBuf);
			encBuf.release();
		}
		out.close();
	}
	 
    public void encodePacket(Packet packet, ByteBuf buf, ByteBufAllocator allocator) throws IOException {
        ByteBuf encBuf = allocateBuffer(allocator);
        ByteBufOutputStream out = new ByteBufOutputStream(encBuf);
        try {
			jsonSupport.write(out, packet);
			if (encBuf != null) {
				buf.writeBytes(encBuf);
			}
        } finally {
        	encBuf.release();
        	out.close();
		}
    }
    
    public byte[] encodePacket(Packet packet) throws IOException {
		return jsonSupport.writeBytes(packet);
    }
    
    public byte[] packetSerializable(Packet packet) throws IOException {
		return jsonSupport.writeBytes(packet);
    }
    
    public String encoderString(Object object) throws IOException {
		return jsonSupport.writeString(object);
    }
}
