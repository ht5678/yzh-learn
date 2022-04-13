package org.demo.netty.im.fake.session;

import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.im.bs.message.OutPacketMessage;
import org.demo.netty.im.fake.im.chain.CloseChatChain;
import org.demo.netty.im.fake.im.constants.Constants;
import org.demo.netty.im.fake.session.monitor.HttpPollCycleMonitor;
import org.demo.netty.im.fake.util.UUIDUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * 用户session
 * @author yuezh2
 * @date	  2022年3月29日 下午3:30:45
 */
public class LocalCustomerSession implements CustomerSession{

	
	
	private String cid;
	private Channel channel;
	private Transport transport;
	private volatile CustomerAssignStatus status = CustomerAssignStatus.UNASSIGNED;
	private String waiterName;
	private String waiterCode;
	private TransportStore store;
	private Identity idy;
	private Customer customer;
	private String version;
	private HttpPollCycleMonitor cycleMonitor;

	private volatile boolean closed = false;
	
	public LocalCustomerSession(Channel channel, Transport transport, Identity idy, Customer customer) {
		this.channel = channel;
		this.transport = transport;
		this.idy = idy;
		this.customer = customer;
		this.store = new LocalTransportStore(transport);
		if (transport == Transport.POLLING) {
			cycleMonitor = new HttpPollCycleMonitor(this);
		}
		this.version = UUIDUtils.getUID();
	}

	@Override
	public String getUid() {
		return customer.getUid();
	}

	@Override
	public String getName() {
		return customer.getName();
	}

	@Override
	public Identity getIdy() {
		return idy;
	}

	@Override
	public String getTenantCode() {
		return customer.getTenantCode();
	}

	@Override
	public String getTeamCode() {
		return customer.getTeamCode();
	}

	@Override
	public String getSkillCode() {
		return customer.getSkillCode();
	}

	@Override
	public String getSkillName() {
		return customer.getSkillName();
	}

	@Override
	public String getGoodsCode() {
		return customer.getGoodsCode();
	}

	@Override
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Override
	public String getCid() {
		return cid;
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

	@Override
	public Transport getTransport() {
		return transport;
	}

	@Override
	public TransportStore transportStore() {
		return store;
	}

	@Override
	public void cachePacket(Packet packet) {
		store.getFuturePackets().add(packet);
	}

	@Override
	public void bindRoute() {
		OCIMServer.getInst().getRoutingTable().registerLocalCustomerSession(this);
	}

	@Override
	public void bindChannel(Channel channel) {
		this.channel = channel;
		if (transport == Transport.POLLING) {
			cycleMonitor.reset();
		}
	}

	@Override
	public ChannelFuture sendPacket(Packet packet) {
		if (transport == Transport.SOCKET) {
			return channel.writeAndFlush(packet);
		}
		store.getPacketsQueue().add(packet);
		return channel.writeAndFlush(new OutPacketMessage(this));
	}
	
	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public CustomerAssignStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(CustomerAssignStatus status) {
		this.status = status;
		if (CustomerAssignStatus.UNASSIGNED.equals(status)) {
			waiterName = null;
			waiterCode = null;
		}
	}
	
	@Override
	public String getWaiterName() {
		return waiterName;
	}
	
	@Override
	public void setWaiter(String waiterCode, String waiterName) {
		this.waiterCode = waiterCode;
		this.waiterName = waiterName;
	}
	
	@Override
	public String getWaiterCode() {
		return waiterCode;
	}
	
	@Override
	public synchronized void disconnect() {
		if (closed) {
			return;
		}
		closed = true;
		OCIMServer.getInst().getDispatcher().removeQueueWait(customer);
		if (this.getStatus() == CustomerAssignStatus.ASSIGNED) {
			CloseChatChain.closeChatToWaiter(this, Constants.DISCONNECT_MESSAGE);
		}
		OCIMServer.getInst().getRoutingTable().removeLocalCustomerSession(this);
	}

	@Override
	public synchronized void closeChannel() {
		if (closed) {
			return;
		}
		closed = true;
		OCIMServer.getInst().getDispatcher().closeChat(this);
		if (null != this.channel) {
			this.channel.close();
		}
	}

	@Override
	public String toString() {
		return "CustomerSessionImpl [cid=" + cid + ", status=" + status + ", waiterName=" + waiterName + ", waiterCode="
				+ waiterCode + ", customer=" + customer + ", version=" + version + "]";
	}
}

