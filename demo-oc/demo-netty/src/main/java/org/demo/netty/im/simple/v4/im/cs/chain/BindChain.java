package org.demo.netty.im.simple.v4.im.cs.chain;

import java.io.IOException;

import org.demo.netty.im.fake.domain.AddressFrom;
import org.demo.netty.im.fake.domain.AddressTo;
import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.coder.PacketEncoder;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.simple.v4.im.OCIMServerV4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月7日 下午10:06:11
 */
public class BindChain {

	private static Logger log = LoggerFactory.getLogger(BindChain.class);
	
	
	
	/**
	 * 客服登录信息绑定, 开始正式服务
	 */
	public static void waiterBind(WaiterSession session , Channel channel , PacketEncoder encoder)throws IOException {
		Waiter waiter = session.getWaiter();
		
		//绑定客服信息
		session.bindRoute();
		//发送登录成功消息
		Body body = new Body(BodyType.SUCCESS, encoder.encoderString(waiter));
		AddressFrom from =  new AddressFrom(Identity.SYS);
		AddressTo to = new AddressTo(Identity.WAITER);
		Packet bindPacket = new Packet(PacketType.BIND , from , to , body);
		session.sendPacket(bindPacket).addListener((ChannelFutureListener)future -> {
			//客服登录成功后 , 设置服务信息
			OCIMServerV4.getInst().getDispatcher().login(waiter);
			log.info("客服工号 : {} , 登录成功 . " , waiter.getWaiterCode());
		});
	} 
	
}
