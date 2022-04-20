package org.demo.netty.im.simple.v3.cache;

import java.nio.charset.StandardCharsets;

import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.im.auth.CustomerInfo;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.routing.RoutingTable;
import org.demo.netty.im.fake.routing.RoutingTableImpl;
import org.demo.netty.im.fake.session.Customer;
import org.demo.netty.im.fake.session.LocalCustomerSession;
import org.demo.netty.im.fake.store.local.LocalTeamStore;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月20日 下午10:31:12
 */
public class RoutingTableDemo {

	
	
	
	public static void main(String[] args) throws Exception{
		//		//init
		//waiter cache routes, customer cache routes ,  (cluster cache imap)
		//waiter session(存到LocalRoutingTable.concurrentMap里) , customer session(存到LocalRoutingTable.concurrentMap里) 
		RoutingTable routingTable = new RoutingTableImpl();
		
		
		//1
//		CustomerSession session = new LocalCustomerSession(channel, transport, Identity.CUSTOMER, customer);
//		if(transport != transport.POLLING) {
//			channel.attr(Session.CLIENT_SESSION).set(session);
//		}
//		session.bindRoute();
		
		//2
//		OCIMServer.getInst().getRoutingTable().registerLocalCustomerSession(this);
		
		
		//fake
		
		Packet packet = new Packet(PacketType.AUTH);
		packet.setTs(Transport.WEBSOCKET);
		
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setTmc("teamcode");
		customerInfo.setSkc("skillcode");
		customerInfo.setSkn("skn");
		customerInfo.setBuc("buc");
		customerInfo.setReal("1");//login
		customerInfo.setCc("uid");//uid
		customerInfo.setCn("cn");//name
		
		String skillName = LocalTeamStore.getInst().getSkillName(customerInfo.getTtc(), customerInfo.getSkc());
		String uid = customerInfo.getCc();
		String name = customerInfo.getCn();
		Transport transport = packet.getTs();
		
		boolean login = "1".equals(customerInfo.getReal()) ? true : false;
		Customer customer = new Customer(uid, name, login, customerInfo.getTtc(), customerInfo.getTmc(), 
					customerInfo.getSkc(), skillName, customerInfo.getGc(), customerInfo.getDevice());
		
		
		
		LocalCustomerSession customerSession = new LocalCustomerSession(null, transport, Identity.CUSTOMER, customer);
		
		//必备条件
		Config config = new Config();
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		OCIMServer ocimServer = new OCIMServer(hazelcastInstance);
		OCIMServer.getInst().setNodeID(new NodeID(hazelcastInstance.getCluster().getLocalMember().getUuid().getBytes(StandardCharsets.UTF_8)));
		
		ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
		
		//开启cluster cache strategy
		CacheFactory.startCluster(hazelcastInstance);
		
		
		//缓存session

		//netty session存到 customerSessionLocalRoutingTable ( = concurrent hash map) 中 , 
		//route (nodeId , version , uid) 存到 hazelcast cluster cache中 的  IMap  . 
		
		
		
//		customerSession.bindRoute();
//		||		-----		等于
		routingTable.registerLocalCustomerSession(customerSession);
		
		
		
		hazelcastInstance.shutdown();
	}
	
}
