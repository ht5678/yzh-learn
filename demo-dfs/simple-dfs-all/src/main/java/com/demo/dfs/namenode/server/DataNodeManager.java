package com.demo.dfs.namenode.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个组件 , 用于负责管理集群里所有的datanode
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午9:40:56
 */
public class DataNodeManager {
	
	//内存中维护的datanode列表
	private List<DataNodeInfo> datanodes = new ArrayList<DataNodeInfo>();
	
	
	/**
	 * datanode进行注册
	 * @param ip
	 * @param hostname
	 */
	public Boolean register(String ip , String hostname) {
		DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
		datanodes.add(datanode);
		return true;
	}
	
	

}
