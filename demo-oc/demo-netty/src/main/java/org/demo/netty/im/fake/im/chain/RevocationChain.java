package org.demo.netty.im.fake.im.chain;

import org.demo.netty.im.fake.domain.AddressFrom;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.fake.store.remote.packet.PacketStoreManager;
import org.demo.netty.im.fake.store.remote.packet.model.RemoteData;
import org.demo.netty.im.fake.store.remote.packet.model.RemoteDataType;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月7日 下午10:15:45
 */
public class RevocationChain {

    public static void revocation(WaiterSession waiterSession, Packet packet) {
        if (null == waiterSession) {
            return;
        }
        /** 处理客服发送客户报文，附加报文来源， 保证报文完整性**/
        AddressFrom from = packet.getFrom() == null ? new AddressFrom() : packet.getFrom();
        from.setUid(waiterSession.getWaiter().getWaiterCode());
        from.setIdy(Identity.WAITER);
        packet.setFrom(from);
        packet.setTtc(waiterSession.getWaiter().getTenantCode());
        packet.setTmc(waiterSession.getWaiter().getTeamCode());

        OCIMServer.getInst().getRoutingTable().routePacket(packet);
        RemoteData remoteData = new RemoteData(RemoteDataType.REVOCATION, packet);
        PacketStoreManager.getInst().addRemoteData(remoteData);
    }
}