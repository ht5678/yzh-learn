package com.learn.dfs.backupnode;

import com.alibaba.fastjson.JSONArray;

/**
 * 从namenode同步editslog组件
 * @author yuezh2@lenovo.com
 *	@date 2022年2月18日下午9:21:39
 */
public class EditsLogFetcher extends Thread{

	private BackupNode backupNode;
	
	private NameNodeRpcClient namenode;
	
	/**
	 * 
	 * @param backupNode
	 */
	public EditsLogFetcher(BackupNode backupNode) {
		this.backupNode = backupNode;
		this.namenode = new NameNodeRpcClient();
	}
	
	
	@Override
	public void run() {
		while(backupNode.isRunning()) {
			JSONArray editsLogs = namenode.fetchEditsLog();
			
		}
	}
	
}




