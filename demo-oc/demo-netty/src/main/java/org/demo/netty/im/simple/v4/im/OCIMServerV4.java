package org.demo.netty.im.simple.v4.im;

import org.demo.netty.im.fake.boot.Server;
import org.demo.netty.im.fake.dispatcher.AllotDispatcher;
import org.demo.netty.im.fake.dispatcher.Dispatcher;
import org.demo.netty.im.fake.routing.RoutingTable;
import org.demo.netty.im.fake.routing.RoutingTableImpl;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;
import org.demo.netty.im.simple.v4.im.server.BSServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月13日 下午5:41:06
 */
public class OCIMServerV4 {
	private static Logger log = LoggerFactory.getLogger(OCIMServerV4.class);
	
	private static OCIMServerV4 instance;
	
	private HazelcastInstance hazelcastInstance;
	
	private BSServer bsServer;
	private Thread bsThread;
	
	private static String BS_THREAD_NAME = "OCIM-BS-Thread-V1";
	
	private RoutingTable routingTable;
	
	private Dispatcher dispatcher;
	
	
	/**
	 * 
	 * @return
	 */
	public static OCIMServerV4 getInst() {
		return instance;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public OCIMServerV4() throws Exception {
		this(null);
	}
	
	/**
	 * 
	 * @param hazelcastInstance
	 * @throws Exception
	 */
	public OCIMServerV4(HazelcastInstance hazelcastInstance)throws Exception {
		if(null != hazelcastInstance) {
			throw new IllegalStateException("已经存在一个OCServer正在运行中 ... ");
		}
		instance = this;
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
	/**
	 * 
	 */
	public void startCluster(boolean isCluster)throws Exception {
		
		ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
		
		this.routingTable = new RoutingTableImpl();
		
		this.dispatcher = new AllotDispatcher();
		
		try {
			bsServer = new BSServer();
		} catch (Exception e) {
			log.error("创建B/S 应用失败.");
			throw new Exception("创建B/S 应用失败", e);
		}
		
		bsThread = new Thread(new ServerStart(bsServer));
		bsThread.setName(BS_THREAD_NAME);
		
		bsThread.start();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public RoutingTable getRoutingTable() {
		return instance.routingTable;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Dispatcher getDispatcher() {
		return instance.dispatcher;
	}
	
	
	
	/**
	 * 后台启动
	 */
	private class ServerStart implements Runnable {
		private final Server server;

		public ServerStart(Server server) {
			this.server = server;
		}

		@Override
		public void run() {
			server.start();
		}
	}	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Config config = new Config();
//		Config config = new ClasspathXmlConfig("hazelcast-dev.xml");
		
//		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		
		HazelcastInstance hazelcastInstance = null;
		
		OCIMServerV4 ocimServer = new OCIMServerV4(hazelcastInstance);
		ocimServer.startCluster(true);
	}
	
}
