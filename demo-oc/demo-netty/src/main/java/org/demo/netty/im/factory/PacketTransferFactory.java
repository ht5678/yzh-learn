package org.demo.netty.im.factory;

import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.provider.redis.IDProvider;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.transfer.TransferTeam;
import org.demo.netty.transfer.TransferWaiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月13日 下午5:25:41
 */
public class PacketTransferFactory {

	private static Logger log = LoggerFactory.getLogger(PacketTransferFactory.class);
	
	public Packet transferByWaiterFailed(String cid, TransferWaiter transferWaiter, String message) {
		Body body = new Body(BodyType.FAIL, message);
		return getCreateTransferPacket(cid, transferWaiter, body);
	}
	
	public Packet transferByWaiterASuccess(String cid, TransferWaiter transferWaiter) {
		Body body = new Body(BodyType.SUCCESS, "提示，客户成功转入。");
		return getCreateTransferPacket(cid, transferWaiter, body);
	}

	public void transferByTeamFailed(String cid, TransferTeam transferTeam, String message) {
		Body body = new Body(BodyType.FAIL, message);
		Packet createTransferPacket = getCreateTransferPacket(cid, transferTeam, body);
		OCIMServer.getInst().getRoutingTable().routePacket(createTransferPacket);
	}

	public void transferByTeamSuccess(String cid, TransferTeam transferTeam, String message) {
		Body body = new Body(BodyType.SUCCESS, message);
		Packet createTransferPacket = getCreateTransferPacket(cid, transferTeam, body);
		OCIMServer.getInst().getRoutingTable().routePacket(createTransferPacket);
	}

	public Packet transferByWaiterBSuccess(CustomerSession customerSession, TransferWaiter transferWaiter) {

		// 重新分配cid 初始化客户基本信息 C
		String cid = IDProvider.getInstance().getChatId();
		customerSession.setCid(cid);
		customerSession.setWaiter(transferWaiter.getToWc(), transferWaiter.getToWn());
		Customer customer = customerSession.getCustomer();
		customer.setTenantCode(transferWaiter.getTtc());
		customer.setTeamCode(transferWaiter.getTmc());
		customer.setSkillCode(transferWaiter.getSkc());
		customer.setSkillName(transferWaiter.getSkn());

		StringBuilder transferReason = new StringBuilder();
		transferReason.append("提示，客户来自工号【 ");
		transferReason.append(transferWaiter.getFromWc());
		transferReason.append(" 】转入， 备注信息：");
		transferReason.append(transferWaiter.getReason());

		String content = BuildChatFactory.createBuildChatToJson(customerSession, transferReason.toString());

		Body body = new Body(BodyType.TRANSFER_WAITER,  content);
		AddressTo to = new AddressTo(transferWaiter.getToWc(), transferWaiter.getToWn(), Identity.WAITER);
		AddressFrom from = new AddressFrom(transferWaiter.getUid(), transferWaiter.getName(),  Identity.CUSTOMER);
		Packet packet = new Packet(PacketType.BUILD_TRANSFER_CHAT, from, to, body);
		packet.setCid(cid);
		packet.setTtc(transferWaiter.getTtc());
		packet.setTmc(transferWaiter.getTmc());
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
		return packet;
	}
	
	/**
	 * 客户发送转接成功信息
	 * @param cid
	 * @param transferWaiter
	 * @return
	 */
	public Packet transferByWaiterCSuccess(CustomerSession customerSession, String cid, TransferWaiter transferWaiter) {
		String content = BuildChatFactory.createBuildChatToJson(customerSession);
		Body body = new Body(BodyType.SUCCESS, content);
		AddressTo to = new AddressTo(transferWaiter.getUid(), transferWaiter.getName(), Identity.CUSTOMER);
		AddressFrom from = new AddressFrom(transferWaiter.getToWc(), Identity.WAITER);
		Packet packet = new Packet(PacketType.TRANSFER, from, to, body);
		packet.setCid(cid);
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
		return packet;
	}

	/**
	 * 创建转接成功失败消息
	 * @param cid
	 * @param transferWaiter
	 * @param body
	 * @return
	 */
	private Packet getCreateTransferPacket(String cid, TransferWaiter transferWaiter, Body body) {
		AddressTo to = new AddressTo(transferWaiter.getFromWc(), Identity.WAITER);
		AddressFrom from = new AddressFrom(transferWaiter.getUid(), transferWaiter.getName(), Identity.CUSTOMER);
		Packet packet = new Packet(PacketType.TRANSFER, from, to, body);
		packet.setCid(cid);
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
		return packet;
	}

	/**
	 * 创建转接成功失败消息
	 * @param cid
	 * @param transferTeam
	 * @param body
	 * @return
	 */
	private Packet getCreateTransferPacket(String cid, TransferTeam transferTeam, Body body) {
		AddressFrom from = new AddressFrom(transferTeam.getUid(), transferTeam.getName(), Identity.CUSTOMER);
		AddressTo to = new AddressTo(transferTeam.getFromWc(), Identity.WAITER);
		Packet packet = new Packet(PacketType.TRANSFER, from, to, body);
		packet.setCid(cid);
		return packet;
	}
	
	public static PacketTransferFactory getInst() {
		return Single.inst;
	}

	private static class Single {
		private static PacketTransferFactory inst = new PacketTransferFactory();
	}
}
