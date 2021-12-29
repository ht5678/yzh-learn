package org.dfs.datanode;

import com.demo.dfs.rpc.model.HeartbeatRequest;
import com.demo.dfs.rpc.model.HeartbeatResponse;
import com.demo.dfs.rpc.model.RegisterRequest;
import com.demo.dfs.rpc.model.RegisterResponse;
import com.demo.dfs.rpc.service.NameNodeServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午9:15:42
 */
public class NameNodeServiceActor {
	
	private static final String NAMEODE_HOSTNAME = "localhost";
	private static final Integer NAMENODE_PORT = 50070;
	
	private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;
	
	
	
	/**
	 * 
	 */
	public NameNodeServiceActor () {
		
		ManagedChannel channel = NettyChannelBuilder
				.forAddress(NAMEODE_HOSTNAME , NAMENODE_PORT)
				.negotiationType(NegotiationType.PLAINTEXT)//消息类型
				.build();
		//
		this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
	}

	
	/**
	 * 向自己负责通信的那个NameNode进行扫描
	 * @param latch
	 */
	public void register() throws Exception{
//	public void register(CountDownLatch latch) {
//		Thread registerThread = new RegisterThread(latch);
		Thread registerThread = new RegisterThread();
		registerThread.start();
		registerThread.join();
	}
	
	
	/**
	 * 开始发送心跳的线程
	 */
	public void startHeartbeat () {
		new HeartbeatThread().start();
	}
	
	
	class HeartbeatThread extends Thread {
		
		@Override
		public void run() {
			try {
				while(true) {
					System.out.println("发送RPC到NameNode进行心跳 ... ");
					
					String ip = "127.0.0.1";
					String hostname = "dfs-data-01";
					//通过RPC接口发送到NameNode他的心跳接口上去
					
					HeartbeatRequest request = HeartbeatRequest.newBuilder()
							.setIp(ip)
							.setHostname(hostname)
							.build();
					HeartbeatResponse response = namenode.heartbeat(request);
					System.out.println("接收到namenode返回的[心跳]响应  : "+response.getStatus());
					
					
					Thread.sleep(30 * 1000);	//每隔30s发送一次心跳到NameNode上去
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 负责注册的线程
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月21日下午9:31:10
	 */
	class RegisterThread extends Thread{
//		CountDownLatch latch;
//		
//		public RegisterThread(CountDownLatch latch) {
//			this.latch = latch;
//		}

		@Override
		public void run() {
			try {
				//发送rpc接口请求到NameNode进行注册
				System.out.println("发送请求到NameNode进行注册 ... ... ");
				
				//在这里进行注册的时候会提供哪些信息 ? 
				//比如说当前机器的ip , hostname , 这两个东西要写到配置文件里的
				//主要在本地运行测试的 , 
				String ip = "127.0.0.1";
				String hostname = "dfs-data-01";
				//通过rpc接口发送到NameNode的注册接口上
				
				RegisterRequest request = RegisterRequest.newBuilder()
								.setIp(ip)
								.setHostname(hostname)
								.build();
				RegisterResponse response = namenode.register(request);
				System.out.println("接收到namenode返回的[注册]响应  : "+response.getStatus());
				
//				Thread.sleep(1000);
//				latch.countDown();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
