package org.demo.netty.session;

import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Transport;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午6:29:12
 */
public class LocalTransportStore implements TransportStore {
	
	private CustomQueue<Packet> packetsQueue = new DefaultCustomQueue<>();
	private CustomQueue<Packet> futurePackets = new DefaultCustomQueue<>();
	
	private Transport transport;
	
	public LocalTransportStore(Transport transport) {
		this.transport = transport;
	}

	@Override
	public CustomQueue<Packet> getPacketsQueue() {
		return packetsQueue;
	}

	@Override
	public CustomQueue<Packet> getFuturePackets() {
		return futurePackets;
	}

	@Override
	public Transport getTransport() {
		return transport;
	}
}
