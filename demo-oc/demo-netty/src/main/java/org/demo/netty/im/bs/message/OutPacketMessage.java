package org.demo.netty.im.bs.message;

import org.demo.netty.domain.Transport;
import org.demo.netty.session.Session;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午5:00:45
 */
public class OutPacketMessage implements Message{

	private final Session session;
	
	public OutPacketMessage(Session session) {
		this.session = session;
	}

	public Transport getTransport() {
		return session.getTransport();
	}

	public Session getSession() {
		return session;
	}
}