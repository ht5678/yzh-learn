package org.dfs.namenode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个组件 , 用于负责管理集群里所有的datanode
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午9:40:56
 */
public class DataNodeManager {
	
	//内存中维护的datanode列表
	private Map<String , DataNodeInfo> datanodes = new ConcurrentHashMap<String , DataNodeInfo>();
	
	
	public DataNodeManager () {
		new DataNodeAliveMonitor().start();
	}
	
	
	/**
	 * datanode进行注册
	 * @param ip
	 * @param hostname
	 */
	public Boolean register(String ip , String hostname) {
		DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
		datanodes.put(ip+"-"+hostname , datanode);
		return true;
	}
	
	/**
	 * datanode进行心跳
	 * @param ip
	 * @param hostname
	 * @return
	 */
	public Boolean heartbeat(String ip , String hostname) {
		DataNodeInfo datanode = datanodes.get(ip+"-"+hostname);
		if(datanode != null) {
			datanode.setLatestHeartbeatTime(System.currentTimeMillis());
		}
		return true;
	}
	

	
	/**
	 * datanode是否存活的监控线程
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月22日下午8:03:38
	 */
	class DataNodeAliveMonitor extends Thread{
		@Override
		public void run() {
			try {
				while(true) {
					List<String> toRemoveDatanodes = new ArrayList<String>();
					
					Iterator<DataNodeInfo> datanodesIterator = datanodes.values().iterator();
					DataNodeInfo datanode = null;
					while(datanodesIterator.hasNext()) {
						datanode = datanodesIterator.next();
						if(System.currentTimeMillis() - datanode.getLatestHeartbeatTime() > 90 * 1000) {
							toRemoveDatanodes.add(datanode.getIp() + "-" + datanode.getHostname());
						}
					}
					
					if(!toRemoveDatanodes.isEmpty()) {
						for(String toRemoveDatanode : toRemoveDatanodes) {
							datanodes.remove(toRemoveDatanode);
						}
					}
					
					Thread.sleep(30 * 1000L);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
