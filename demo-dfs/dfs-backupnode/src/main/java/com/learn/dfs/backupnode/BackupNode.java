package com.learn.dfs.backupnode;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年2月18日下午9:23:41
 */
public class BackupNode {

	private volatile Boolean isRunning = true;
	
	private FSNamesystem namesystem;
	
	public static void main(String[] args) {
		BackupNode backupNode = new BackupNode();
		backupNode.init();
		backupNode.start();
		
	}
	
	
	public void init() {
		this.namesystem = new FSNamesystem(); 
	}
	
	
	public void start() {
		EditsLogFetcher editsLogFetcher = new EditsLogFetcher(this , namesystem);
		editsLogFetcher.start();
		

	}
	
	
	
	public void run() throws Exception{
		while(isRunning) {
			Thread.sleep(1000);
		}
	}
	
	
	public Boolean isRunning() {
		return isRunning;
	}
	
}
