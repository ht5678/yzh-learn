package org.demo.netty.im.simple.v4.im.bs.oauth;

import org.demo.netty.im.fake.boot.WaiterProvider;
import org.demo.netty.im.fake.domain.AddressFrom;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.exception.BSAuthorizeException;
import org.demo.netty.im.fake.im.auth.CustomerInfo;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.bs.message.PollOkMessage;
import org.demo.netty.im.fake.session.Customer;
import org.demo.netty.im.fake.session.CustomerSession;
import org.demo.netty.im.fake.session.Session;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.fake.store.local.LocalTeamStore;
import org.demo.netty.im.fake.util.B64;
import org.demo.netty.im.simple.v4.im.session.LocalCustomerSession;
import org.demo.netty.im.simple.v4.im.session.LocalWaiterSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:33:33
 */
public class CertificationCenter {

	private Logger log = LoggerFactory.getLogger(CertificationCenter.class);
	private final Configuration config;
	
	/**
	 * 
	 */
	public CertificationCenter(Configuration config) {
		this.config = config;
	}
	
	
	/**
	 * 
	 */
	public void handlerWebSocket(ChannelHandlerContext ctx , Packet packet , FullHttpRequest req) throws Exception {
		Channel channel = ctx.channel();
		AddressFrom from = packet.getFrom();
		if(packet.getBody().getType() == BodyType.LOGIN) {
			if(from.getIdy() == Identity.WAITER) {
				authorizeByWaiter(channel, packet, req);
			}else if(from.getIdy() == Identity.CUSTOMER) {
				authorizeByCustomer(channel, packet, req);
			}else {
				throw new IllegalArgumentException("不支持的用户类型");
			}
		}else {
			throw new IllegalArgumentException("认证授权失败");
		}
		//upgrade websocket
		ctx.fireChannelRead(req);
	}
	
	
	
	/**
	 * 
	 * @param ctx
	 * @param packet
	 * @param req
	 * @throws Exception
	 */
	public void handlerHttp(ChannelHandlerContext ctx , Packet packet , FullHttpRequest req)throws Exception {
		Channel channel = ctx.channel();
		if(packet.getBody().getType() == BodyType.LOGIN) {
			authorizeByCustomer(channel, packet, req);
			channel.writeAndFlush(new PollOkMessage());
		}else {
			throw new IllegalArgumentException("认证授权失败");
		}
		req.release();
	}
	
	
	
	
	/**
	 * 验证用户 , 并创建连接
	 * @return
	 */
	private Session authorizeByWaiter(Channel channel , Packet packet , FullHttpRequest req) throws Exception{
		WaiterSession session;
		String content = packet.getBody().getContent();
		String decoder = B64.decoder(content);
		String[] infos = decoder.split(" ");
		String userName = null;
		String password = null;
		final String status;
		
		if(infos.length == 3) {
			userName = infos[0];
			password = infos[1];
			status = infos[2];
		}else {
			log.error("请求缺少参数:{}" , req.uri());
			throw new BSAuthorizeException("认证信息用户失败 , 缺少请求参数");
		}
		
		Waiter waiter = WaiterProvider.getInst().authentication(userName, password);
		if(null != waiter) {
			waiter.setStatus(status);
			String uid = waiter.getWaiterCode();
			Transport transport = packet.getTs();
			session = new LocalWaiterSession(uid, channel, transport, Identity.WAITER, waiter);
			channel.attr(LocalWaiterSession.CLIENT_SESSION).set(session);
			//记录客服登录消息
			WaiterProvider.getInst().insertWaiterLog(waiter, "1", req.headers().get("X-Real-IP"));
		}else {
			log.warn("认证失败 , 地址:{} , 头信息: {}" , req.uri() , req.headers());
			throw new BSAuthorizeException("认证用户信息失败 , 账号或者密码有误");
		}
		return session;
	}
	
	
	
	/**
	 * 验证用户 , 并且创建连接
	 * @param channel
	 * @param packet
	 * @param req
	 * @return
	 */
	private Session authorizeByCustomer(Channel channel  , Packet packet , FullHttpRequest req) throws Exception{
//		CustomerInfo customerInfo = CustomerAuthCoder.decode(req.headers());
		//TODO:
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setTmc("teamcode");
		customerInfo.setSkc("skillcode");
		customerInfo.setSkn("skn");
		customerInfo.setBuc("buc");
		customerInfo.setReal("1");//login
		customerInfo.setCc("uid");//uid
		customerInfo.setCn("cn");//name
		
//		if(null == customerInfo) {
//			throw new BSAuthorizeException("1003认证信息为空");
//		}
		
		String skillName = LocalTeamStore.getInst().getSkillName(customerInfo.getTtc(), customerInfo.getSkc());
		String uid = customerInfo.getCc();
		String name = customerInfo.getCn();
		Transport transport = packet.getTs();
		boolean login = "1".equals(customerInfo.getReal()) ? true : false;
		Customer customer = new Customer(uid, name, login, customerInfo.getTtc(), customerInfo.getTmc(), 
					customerInfo.getSkc(), skillName, customerInfo.getGc(), customerInfo.getDevice());
		CustomerSession session = new LocalCustomerSession(channel, transport, Identity.CUSTOMER, customer);
		if(transport != transport.POLLING) {
			channel.attr(Session.CLIENT_SESSION).set(session);
		}
		session.bindRoute();
		return session;
	}
	
}
