package com.learn.dfs.backupnode;


/**
 * 
 * fsimage的checkpoint组件
 * 
 * @author yuezh2
 * @date	  2022年3月2日 下午6:04:33
 */
public class FsImageCheckpointer extends Thread{
	
	/**
	 * checkpoint定时触发
	 */
	private static final Integer CHECKPOINT_INTERVAL = 1000 * 60 * 60 *1;

	private BackupNode backupNode;
	
	private FSNamesystem namesystem;
	
	
	
	
	public FsImageCheckpointer(BackupNode backupNode , FSNamesystem namesystem) {
		this.backupNode = backupNode;
		this.namesystem = namesystem;
	}
	
	
	@Override
	public void run() {
		System.out.println("fsimage checkpoint定时调度线程启动... ...");
		while(backupNode.isRunning()) {
			try {
				Thread.sleep(CHECKPOINT_INTERVAL);
				
				//可以触发这个checkpoint操作， 去把内存里的数据写入磁盘就可以了
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
