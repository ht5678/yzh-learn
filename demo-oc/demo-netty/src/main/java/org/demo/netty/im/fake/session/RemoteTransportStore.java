package org.demo.netty.im.fake.session;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Transport;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午6:30:31
 */
public class RemoteTransportStore implements TransportStore {

    private final static String PREFIX_CUSTOMER_PACKET = "Customer Packet:";
    private final static String PREFIX_CUSTOMER_FUTURE_PACKET = "Customer Future Packet:";

    private NameFactory nameFactory;

    private CustomQueue<Packet> packetsQueue;
    private CustomQueue<Packet> futurePackets;

    private Transport transport;

    public RemoteTransportStore(Transport transport, String uid) {
        nameFactory = new NameFactory();
        packetsQueue = nameFactory.getQueue(PREFIX_CUSTOMER_PACKET, uid);
        futurePackets = nameFactory.getQueue(PREFIX_CUSTOMER_FUTURE_PACKET, uid);
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

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
