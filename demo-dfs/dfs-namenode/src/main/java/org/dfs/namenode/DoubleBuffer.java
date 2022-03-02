package org.dfs.namenode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.dfs.namenode.FSEditlog.EditLog;

/**
 * 内存双缓冲
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午3:40:58
 */
public class DoubleBuffer{
	
	/*
	 * 单块editlog缓冲区最大的大小, 512kb
	 */
	public static final Integer EDIT_LOG_BUFFER_LIMIT = 20 * 1024;
	
	/*
	 * 是专门用来承载线程写入edits log
	 */
	private EditLogBuffer currentBuffer = new EditLogBuffer();
	/*
	 * 专门用来将数据同步到磁盘中去的一块缓冲
	 */
	private EditLogBuffer syncBuffer = new EditLogBuffer();
	
	/*
	 * 当前这块缓冲区写入的最大txid
	 */
//	long maxTxId = 1L;
	
	/*
	 * 上一次flush到磁盘的时候最大txid是多少
	 */
	long startTxid = 0L;
	
	/**
	 * 已经刷入磁盘中的txid, 范围
	 */
	private List<String> flushedTxids = new ArrayList<>();
	
	/*
	 * 当前写入的最大txid
	 */
//	private Long maxTxId = 0L;
	
	
	/**
	 * 将edits log写到内存缓冲里去
	 * @throws IOException 
	 */
	public void write(EditLog log) throws IOException {
//		currentBuffer.add(log);
		currentBuffer.write(log);
//		maxTxId = log.getTxid();
	}
	
	
	/**
	 * 获取当前写入的最大txid
	 * @return
	 */
//	public Long getMaxTxId(){
//		return maxTxId;
//	}
	
	/**
	 * 判断当前缓冲区是否写满了 , 刷到磁盘
	 * @return
	 */
	public boolean shouldSyncToDisk() {
		if(currentBuffer.size() >= EDIT_LOG_BUFFER_LIMIT){
			return true;
		}
		return false;
	}

	/*
	 * 刷写磁盘
	 */
	public void flush() throws IOException{
//		for(EditLog log : syncBuffer) {
//			//把数据写到磁盘上
//			System.out.println("存入磁盘日志信息: "+log);
//		}
//		syncBuffer.clear();
		syncBuffer.flush();
		syncBuffer.clear();
	}

	/**
	 * 获取内存2里的日志的最大事务编号
	 * @return
	 */
//	public Long getSyncMaxTxid() {
//		return syncBuffer.getLast().txid;
//	}

	
	/**
	 * 交换两块缓冲区,为了同步内存数据到磁盘做准备
	 */
	public void setReadyToSync() {
		EditLogBuffer tmp = currentBuffer;
		currentBuffer = syncBuffer;
		syncBuffer = tmp;
	}
	
	
	/**
	 * 获取已经刷入磁盘的editslog数据
	 * @return
	 */
	public List<String> getFlushedTxids() {
		return flushedTxids;
	}
	
	
	/**
	 * 获取当前缓冲区里的数据
	 * @return
	 */
	public String[] getBufferedEditsLog() {
		if(currentBuffer.size()==0) {
			return null;
		}
		String editsLogRawData = new String(currentBuffer.getBufferData());
		return editsLogRawData.split("\n");
	}
	
	
	/**
	 * editlog缓冲区
	 * @author yue
	 *
	 */
	class EditLogBuffer {
		
		/*
		 * 针对内存缓冲区的字节数组输出液
		 */
		ByteArrayOutputStream buffer = null;
		
		/*
		 * 磁盘上的editslog日志文件的channel
		 */
		FileChannel editsLogFileChannel ;
		
		long endTxId = 0L;
		
		
		public EditLogBuffer() {
			buffer = new ByteArrayOutputStream(EDIT_LOG_BUFFER_LIMIT * 2);
			
//			String editsLogFilePath = "d:\\data\\dfs-editslog.log";
//			try {
//				RandomAccessFile file = new RandomAccessFile(editsLogFilePath, "rw");	//读写模式 , 数据写入
//				FileOutputStream fos = new FileOutputStream(file.getFD());
//				this.editsLogFileChannel = fos.getChannel();
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
		}
		
		
		/**
		 * editlog写入缓冲区
		 */
		public void write(EditLog log)throws IOException{
			endTxId = log.getTxid();
			System.out.println( " , 当前缓冲区的大小 : "+this.size());
			buffer.write(log.getContent().getBytes());
			buffer.write("\r\n".getBytes());
			System.out.println("在currentBuffer中写入一条数据 : "+log.getContent() + " , 当前缓冲区的大小 : "+this.size());
		}
		
		
		/**
		 * 获取当前缓冲区已经写入数据的字节数量
		 * @return
		 */
		public Integer size() {
			return buffer.size();
		}
		
		
		/**
		 * 将syncBuffer中的数据刷入磁盘
		 */
		public void flush()throws IOException{
			byte[] data = buffer.toByteArray();
			ByteBuffer dataBuffer = ByteBuffer.wrap(data);
			
			String editsLogFilePath = String.format("d:\\data\\edits-%s-%s.log" , ++startTxid , endTxId);
			flushedTxids.add(startTxid + "_" + endTxId);
			
			try {
				
				File editsLogFile = new File(editsLogFilePath);
				if(!editsLogFile.exists()) {
					editsLogFile.createNewFile();
				}
				
				RandomAccessFile file = null;
				FileOutputStream fos = null; 
						
				try {
					file = new RandomAccessFile(editsLogFilePath, "rw");	//读写模式 , 数据写入
					fos = new FileOutputStream(file.getFD());
					this.editsLogFileChannel = fos.getChannel();
					
					editsLogFileChannel.write(dataBuffer);
					editsLogFileChannel.force(false);		//强制把数据刷到磁盘上
				}finally {
					if(file!= null) {
						file.close();
					}
					
					if(fos!=null) {
						fos.close();
					}
					
					if(editsLogFileChannel!=null) {
						editsLogFileChannel.close();
					}
				}
				
				startTxid = endTxId;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 清空内存缓冲的数据
		 */
		public void clear(){
			buffer.reset();
		}
		
		
		/**
		 * 获取内存缓冲区当前的数据
		 * @return
		 */
		public byte[] getBufferData() {
			return buffer.toByteArray();
		}
		
	}
	
}