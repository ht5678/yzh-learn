package org.demo.netty.im.fake.im.chain;

import java.io.IOException;

import org.demo.netty.im.fake.domain.AddressFrom;
import org.demo.netty.im.fake.domain.AddressTo;
import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.transfer.TransferTeam;
import org.demo.netty.im.fake.transfer.TransferWaiter;
import org.demo.netty.im.fake.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午3:46:14
 */
public class TransferChain {

	private static Logger log = LoggerFactory.getLogger(TransferChain.class);
	
	
	/**
	 * 转接客户
	 */
	public static void transfer(Packet packet) {
		Body body = packet.getBody();
		switch (body.getType()) {
			case TRANSFER_WAITER:
				transferByWaiter(packet);
				break;
			case TRANSFER_TEAM:
				transferByTeam(packet);
				break;
			default:
				log.error("不支持的转接类型，packet{}", packet);
				break;
		}
	}
	
	
	/**
	 * 按照客服转接客户
	 */
	private static void transferByWaiter(Packet packet) {
		String transferJsonStr = packet.getBody().getContent();
		TransferWaiter transferWaiter = null;
		try {
			transferWaiter = JsonUtils.getJson().readClass(transferJsonStr, TransferWaiter.class);
		}catch(IOException e) {
			log.error("转接序列化失败 , packet : {}" , packet);
		}
		
		//
		if(null == transferWaiter) {
			errorParam(packet);
		}else {
			OCIMServer.getInst().getRoutingTable().routeTransferByWaiter(transferWaiter);
		}
	}
	
	
	
	
	private static void transferByTeam(Packet packet) {
		String transferJsonStr = packet.getBody().getContent();
		TransferTeam transferTeam = null;
		try {
			transferTeam = JsonUtils.getJson().readClass(transferJsonStr, TransferTeam.class);
		} catch (IOException e) {
			log.error("转接序列化失败， packet： {}", packet);
		}
		if (null == transferTeam) {
			errorParam(packet);
		} else {
			OCIMServer.getInst().getRoutingTable().routeTransferByTeam(transferTeam);
		}
	}
	
	
	
	/**
	 * 
	 */
	private static void errorParam(Packet packet) {
		AddressTo to = new AddressTo(packet.getFrom().getUid() , Identity.WAITER);
		AddressFrom from = new AddressFrom(packet.getTo().getUid() , 
					packet.getTo().getName() , packet.getTo().getIdy());
		Body body = new Body(BodyType.FAIL , "转接客户失败 , 请重试 . ");
		packet.setTo(to);
		packet.setFrom(from);
		packet.setBody(body);
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
	}
	
}
