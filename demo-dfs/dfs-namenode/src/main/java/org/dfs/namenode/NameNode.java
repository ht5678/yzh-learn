package org.dfs.namenode;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午8:55:40
 */
public class NameNode {

	/**
	 * 核心 , 分布式文件系统的文件目录树 , 管理元数据 ; 支持权限设置
	 */
	private FSNamesystem namesystem;
	/**
	 * 负责管理集群里的所有datanode
	 */
	private DataNodeManager dataNodeManager;
	/**
	 * namenode对外提供rpc接口的server , 可以响应请求
	 */
//	private NameNodeServiceImpl rpcServer;
	private NameNodeRpcServer rpcServer;
	
	//NameNode是否运行
//	private volatile boolean shouldRun = false;
	
	
//	public NameNode() {
//		this.shouldRun = true;
//	}
	
	
	/**
	 * 初始化namenode
	 */
	private void initialize () throws Exception {
		this.namesystem = new FSNamesystem();
		this.dataNodeManager = new DataNodeManager();
//		this.rpcServer = new NameNodeServiceImpl(this.namesystem , this.dataNodeManager);
		this.rpcServer = new NameNodeRpcServer(this.namesystem, this.dataNodeManager);
//		this.rpcServer.start();
//		this.rpcServer.blockUntilShutdown();
	}	
	
	
	/**
	 * 让NameNode运行起来
	 */
	private void start() {
		try {
//			while(shouldRun) {//判断是否停止 
//				Thread.sleep(1000);
//			}
			this.rpcServer.start();
			this.rpcServer.blockUntilShutdown();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws Exception{
		NameNode namenode = new NameNode();
		namenode.initialize();
//		namenode.run();
		namenode.start();
	}
	
}
