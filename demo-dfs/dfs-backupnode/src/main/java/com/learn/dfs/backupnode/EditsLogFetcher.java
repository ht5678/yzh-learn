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
			try{
				JSONArray editsLogs = namenode.fetchEditsLog();
				
				if(editsLogs.size() ==0){
					System.out.println("没有收到任何一条editslog , 等待1s后继续尝试拉取");
					Thread.sleep(1000);
					continue;
				}
				
				for(int i = 0 ; i < editsLogs.size() ; i++) {
					JSONObject editsLog = editsLogs.getJSONObject(i);
					System.out.println("拉取到一条editslog : "+editsLog.toJSONString());
					String op = editsLog.getString("OP");
					
					if(op.equals("MKDIR")) {
						String path = editsLog.getString("PATH");
						namesystem.mkdir(path);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}




