/**
 * 
 */
package org.demo.netty.util;

import java.io.IOException;
import java.net.URLDecoder;

import com.fasterxml.jackson.databind.JsonNode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yue
 *
 */
public class PacketDecoder {

	private final JsonSupport jsonSupport;

	public PacketDecoder(JsonSupport jsonSupport) {
		this.jsonSupport = jsonSupport;
	}

	public ByteBuf preprocessJson(Integer jsonIndex, ByteBuf content) throws IOException {
		String packet = URLDecoder.decode(content.toString(CharsetUtil.UTF_8), CharsetUtil.UTF_8.name());

		return Unpooled.wrappedBuffer(packet.getBytes(CharsetUtil.UTF_8));
	}

	public <T> T decodePackets(Object packet, Class<T> clazz) throws IOException {
		return jsonSupport.readClass(packet, clazz);
	}
	
	public JsonNode decodeObject(Object o) throws IOException {
		return (JsonNode)jsonSupport.read(o);
	}
	
	public <T> T decodeJsonParse(Object o, Class<T> clazz) throws IOException {
		
		return jsonSupport.readClass(o, clazz);
	}
}
