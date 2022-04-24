package org.demo.netty;

import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.im.constants.Constants;
import org.demo.netty.im.fake.util.JsonUtils;
import org.junit.Test;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月22日 下午4:21:21
 */
public class TestJsonUtil {
	
	
	/**
	 * message - packet , 
	 */
	@Test
	public void test3() throws Exception{
		
		Body body = new Body(BodyType.BUILDING_CHAT, Constants.ASSIGNING_MESSAGE);
		
		Packet packet = new Packet(PacketType.MESSAGE, body);
		packet.setTs(Transport.WEBSOCKET);
		packet.setCid("cid");

		System.out.println(JsonUtils.getJson().writeString(packet));
	}
	
	
	
	/**
	 * build chat - packet , 
	 */
	@Test
	public void test2() throws Exception{
		
		Body body = new Body(BodyType.BUILDING_CHAT, Constants.ASSIGNING_MESSAGE);
		
		Packet packet = new Packet(PacketType.BUILD_CHAT, body);
		packet.setTs(Transport.WEBSOCKET);
		packet.setCid("cid");

		System.out.println(JsonUtils.getJson().writeString(packet));
	}
	
	
	
	/**
	 * ping - packet
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception{
		Packet packet = new Packet();
		packet.setBody(new Body(BodyType.TEXT, "test body"));
		packet.setTs(Transport.WEBSOCKET);
		packet.setCid("cid");
//		packet.setType(PacketType.MESSAGE);
		
		
		packet.setType(PacketType.PING);
		
		System.out.println(JsonUtils.getJson().writeString(packet));
	}
	

}
