package org.demo.netty.store.remote.packet.model;

import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.WaiterSession;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月31日 下午10:51:16
 */
public class RemoteData {

	private RemoteDateType type;

	private Packet packet;
	private Identity identity;
	private CustomerSession customerSession;
	private WaiterSession waiterSession;

	public RemoteData(RemoteDateType type, Packet packet) {
		this.type = type;
		this.packet = packet;
	}

	public RemoteData(RemoteDateType type, Packet packet, WaiterSession session) {
		this.type = type;
		this.packet = packet;
		this.identity = session.getIdy();
		this.waiterSession = session;
	}
	
	public RemoteData(RemoteDateType type, Packet packet, CustomerSession session) {
		this.type = type;
		this.packet = packet;
		this.identity = session.getIdy();
		this.customerSession = session;
	}

	public RemoteDateType getType() {
		return type;
	}

	public void setType(RemoteDateType type) {
		this.type = type;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public CustomerSession getCustomerSession() {
		return customerSession;
	}

	public void setCustomerSession(CustomerSession customerSession) {
		this.customerSession = customerSession;
	}

	public WaiterSession getWaiterSession() {
		return waiterSession;
	}

	public void setWaiterSession(WaiterSession waiterSession) {
		this.waiterSession = waiterSession;
	}

	@Override
	public String toString() {
		return "RemoteData [packet=" + packet + ", identity=" + identity + ", customerSession=" + customerSession
				+ ", waiterSession=" + waiterSession + "]";
	}
}
