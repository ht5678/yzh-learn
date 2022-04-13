package org.demo.netty.im.fake.im.cs.heart;

import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午4:43:25
 */
public class HeartDetector {

	private static Logger log = LoggerFactory.getLogger(HeartDetector.class);
	
	
	public static void pongCs(Channel channel) {
		if(log.isDebugEnabled()) {
			Session session = channel.attr(Session.CLIENT_SESSION).get();
			log.debug("响应客户端:{} , PING请求:{}" , session.getUid());
		}
		
		Packet packet = new Packet(PacketType.PONG);
		Body body = new Body(BodyType.TEXT , String.valueOf(System.currentTimeMillis()));
		packet.setBody(body);
		channel.writeAndFlush(packet);
	}
	
	
	public static void pongBs(Session session) {
		Packet pingPacket = new Packet(PacketType.PONG);
		Body body = new Body(BodyType.TEXT, String.valueOf(System.currentTimeMillis()));
		pingPacket.setBody(body);
		session.sendPacket(pingPacket);
	}
	
	
	
}
