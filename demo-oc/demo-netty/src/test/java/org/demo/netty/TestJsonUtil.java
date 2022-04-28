package org.demo.netty;

import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.domain.WaiterStatus;
import org.demo.netty.im.fake.im.constants.Constants;
import org.demo.netty.im.fake.util.B64;
import org.demo.netty.im.fake.util.JsonUtils;
import org.junit.Test;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月22日 下午4:21:21
 */
public class TestJsonUtil {
	
	
	/**
	 * customer - close chat transport
	 */
	@Test
	public void test5() throws Exception{
		String json = "{\"cid\":\"cid\",\"type\":\"CLOSE\",\"body\":{\"type\":\"TEXT\",\"content\":\"close customer chat body content\"},\"ts\":\"TRANSPORT\",\"timestamp\":1650617030335,\"datetime\":\"2022-04-22 16:43:50\",\"ver\":\"1.0\"}";
		
		Packet packet = JsonUtils.getJson().readClass(json, Packet.class);
		System.out.println(JsonUtils.getJson().writeString(packet));
	}
	
	
	
	/**
	 * waiter - auth login
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception{
		//customer - auth login
		String json = "{\"type\":\"AUTH\",\"ts\":\"WEBSOCKET\",\"from\":{\"idy\":\"CUSTOMER\"},\"body\":{\"type\":\"LOGIN\"}}";
		
		Packet packet = JsonUtils.getJson().readClass(json, Packet.class);
		packet.getFrom().setIdy(Identity.WAITER);
		
		Body body = packet.getBody();
		body.setContent("zhangsan password "+WaiterStatus.ONLINE.getValue());
		body.setContent(B64.encoder(body.getContent()));
		
		System.out.println(JsonUtils.getJson().writeString(packet));
		System.out.println(B64.encoder(JsonUtils.getJson().writeString(packet)));
	}
	
	
	
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
