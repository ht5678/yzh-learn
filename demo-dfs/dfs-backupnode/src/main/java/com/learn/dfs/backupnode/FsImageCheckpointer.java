package com.learn.dfs.backupnode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
	private static final Integer CHECKPOINT_INTERVAL = 1000 * 60 * 1;

	private BackupNode backupNode;
	
	private FSNamesystem namesystem;
	
	private String lastFsImageFile="";
	
	
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
				//在写数据的过程中 ， 
				System.out.println("准备执行checkpoint操作 ， 写入fsimage文件。。。");
				FsImage fsimage = namesystem.getFsImage();
				
				//
				removeLastFsimageFile();
				
				//
				doCheckpoint(fsimage);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 将fsimage持久化到磁盘上去
	 * @param fsimage
	 * @throws Exception
	 */
	private void doCheckpoint(FsImage fsimage) throws Exception{
		ByteBuffer buffer = ByteBuffer.wrap(fsimage.getFsImageJson().getBytes());
		
		//fsimage的文件名的格式 ， 他应该是包含了当前这个里面最后一个editslog的txid
		String fsImageFilePath = String.format("d:\\data\\fsimage-%s.meta" , fsimage.getMaxTxid());
		lastFsImageFile = fsImageFilePath;
		RandomAccessFile file = null;
		FileOutputStream fos = null; 
		FileChannel channel = null;
		try {
			file = new RandomAccessFile(fsImageFilePath, "rw");	//读写模式 , 数据写入
			fos = new FileOutputStream(file.getFD());
			channel = fos.getChannel();
			
			channel.write(buffer);
			channel.force(false);		//强制把数据刷到磁盘上
		}finally {
			if(file!= null) {
				file.close();
			}
			
			if(fos!=null) {
				fos.close();
			}
			
			if(channel!=null) {
				channel.close();
			}
		}
	}
	
	
	/**
	 * 删除上一个fsimage磁盘文件
	 * @throws Exception
	 */
	private void removeLastFsimageFile() throws Exception{
		File file = new File(lastFsImageFile);
		if(file.exists()) {
			file.delete();
		}
	}
	
}
