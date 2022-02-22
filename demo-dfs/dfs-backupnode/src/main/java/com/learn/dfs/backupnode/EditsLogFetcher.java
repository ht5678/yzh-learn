package com.learn.dfs.backupnode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 从namenode同步editslog组件
 * @author yuezh2@lenovo.com
 *	@date 2022年2月18日下午9:21:39
 */
public class EditsLogFetcher extends Thread{

	private BackupNode backupNode;
	
	private NameNodeRpcClient namenode;
	
	private FSNamesystem namesystem;
	
	
	/**
	 * 
	 * @param backupNode
	 */
	public EditsLogFetcher(BackupNode backupNode , FSNamesystem namesystem) {
		this.backupNode = backupNode;
		this.namenode = new NameNodeRpcClient();
		this.namesystem = namesystem;
	}
	
	
	@Override
	public void run() {
		while(backupNode.isRunning()) {
			JSONArray editsLogs = namenode.fetchEditsLog();
			for(int i = 0 ; i < editsLogs.size() ; i++) {
				JSONObject editsLog = editsLogs.getJSONObject(i);
				String op = editsLog.getString("OP");
				
				if(op.equals("MKDIR")) {
					String path = editsLog.getString("PATH");
					namesystem.mkdir(path);
				}
			}
		}
	}
	
}




