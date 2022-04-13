package org.demo.netty.im.fake.session;

import org.demo.netty.im.fake.boot.WaiterProvider;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.im.bs.message.OutPacketMessage;
import org.demo.netty.im.fake.util.UUIDUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午4:24:55
 */
public class LocalWaiterSession implements WaiterSession{
	
	
	private String uid;
	private Channel channel;
	private Transport transport;
	private TransportStore store;
	private Identity idy;
	private Waiter waiter;
	private String version;
	
	private volatile boolean closed = false;
	
	
	/**
	 * 
	 */
	public LocalWaiterSession(String uid , Channel channel , Transport transport , Identity idy , Waiter waiter) {
		this.uid = uid;
		this.channel = channel;
		this.transport = transport;
		this.idy = idy;
		this.waiter = waiter;
		this.store = new LocalTransportStore(transport);
		this.version = UUIDUtils.getUID();
	}
	
	
	

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getName() {
		return waiter.getWaiterName();
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public Identity getIdy() {
		return idy;
	}

	@Override
	public void bindRoute() {
		OCIMServer.getInst().getRoutingTable().registerLocalWaiterSession(this);
	}

	@Override
	public void bindChannel(Channel channel) {
		this.channel = channel;
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
	public ChannelFuture sendPacket(Packet packet) {
		if(transport == Transport.SOCKET) {
			return channel.writeAndFlush(packet);
		}
		store.getPacketsQueue().add(packet);
		return channel.writeAndFlush(new OutPacketMessage(this));
	}

	@Override
	public void disconnect() {
		if(closed) {
			return;
		}
		
		closed = true;
		OCIMServer.getInst().getRoutingTable().removeLocalWaiterSession(this);
		//记录登出日志
		waiter.setStatus("4");
		WaiterProvider.getInst().insertWaiterLog(waiter, "3", null);
	}

	@Override
	public void closeChannel() {
		if(null != this.channel) {
			this.channel.close();
		}
	}

	@Override
	public Waiter getWaiter() {
		return waiter;
	}

	@Override
	public String toString() {
		return "WaiterSessionImpl [uid=" + uid + ", version=" + version + ", waiter=" + waiter + "]";
	}
	
}
