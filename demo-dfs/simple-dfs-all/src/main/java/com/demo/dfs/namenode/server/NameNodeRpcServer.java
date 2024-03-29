package com.demo.dfs.namenode.server;


/**
 * NameNode的rpc服务接口
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午8:58:04
 */
public class NameNodeRpcServer {
	
	/**
	 * 负责管理元数据的核心组件
	 */
	private FSNamesystem namesystem;
	
	private DataNodeManager dataNodeManager;
	
	
	/**
	 * 
	 * @param namesystem
	 */
	public NameNodeRpcServer(FSNamesystem namesystem , DataNodeManager dataNodeManager) {
		this.namesystem = namesystem;
		this.dataNodeManager = dataNodeManager;
	}
	
	
	/**
	 * 创建目录
	 * @param path 目录路径
	 * @return		是否创建成功
	 * @throws Exception
	 */
	public Boolean mkdir(String path) throws Exception {
		return this.namesystem.mkdir(path);
	}
	
	
	/**
	 * datanode进行注册
	 * @param ip
	 * @param hostname
	 * @return
	 */
	public Boolean register(String ip , String hostname)throws Exception {
		return dataNodeManager.register(ip, hostname);
	}
	
	
	/**
	 * 启动这个rpc server
	 */
	public void start() {
		System.out.println("开始监听指定的rpc server端口号 , 来接收请求");
	}
	
}
