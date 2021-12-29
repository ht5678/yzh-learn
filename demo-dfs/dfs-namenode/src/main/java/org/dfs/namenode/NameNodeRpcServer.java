package org.dfs.namenode;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.dfs.rpc.service.NameNodeServiceGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月29日下午5:17:10
 */
public class NameNodeRpcServer {
	
	private static final int DEFAULT_PORT = 50070;
	
	private Server server = null;
	
	/**
	 * 负责管理元数据的核心组件
	 */
	private FSNamesystem namesystem;
	
	/**
	 * 负责管理集群中所有的datanode组件
	 */
	private DataNodeManager dataNodeManager;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NameNodeRpcServer.class);
	
	
	
	/**
	 * 
	 * @param namesystem
	 * @param dataNodeManager
	 */
	public NameNodeRpcServer(FSNamesystem namesystem , DataNodeManager dataNodeManager) {
		this.namesystem = namesystem;
		this.dataNodeManager = dataNodeManager;
	}
	
	
	
	
	/**
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException{
		//启动一个rpc server , 监听指定的端口号 ,
		//同时绑定好了自己开发的接口
		server = ServerBuilder
					.forPort(DEFAULT_PORT)
					.addService(NameNodeServiceGrpc.bindService(new NameNodeServiceImpl(namesystem , dataNodeManager)))
					.build()
					.start();
		
		LOGGER.info("server started , listening on "+DEFAULT_PORT);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("*** shutting down grpc server since JVM is shutting down");
				NameNodeRpcServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}
	
	
	
	public void stop() {
		if(server != null) {
			server.shutdown();
		}
	}
	
	
	
	public void blockUntilShutdown () throws InterruptedException {
		if(server != null) {
			server.awaitTermination();
		}
	}
	
	
//	public static void main(String[] args) throws Exception{
//		final namenoderpc
//	}
	

}
