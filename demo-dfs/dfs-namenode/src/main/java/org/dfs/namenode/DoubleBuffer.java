package org.dfs.namenode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.dfs.namenode.FSEditlog.EditLog;

import com.google.common.io.ByteArrayDataOutput;

/**
 * 内存双缓冲
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午3:40:58
 */
public class DoubleBuffer{
	
	/*
	 * 单块editlog缓冲区最大的大小, 512kb
	 */
	public static final Integer EDIT_LOG_BUFFER_LIMIT = 512 * 1024;
	
	/*
	 * 是专门用来承载线程写入edits log
	 */
	private EditLogBuffer currentBuffer = new EditLogBuffer();
	/*
	 * 专门用来将数据同步到磁盘中去的一块缓冲
	 */
	private EditLogBuffer syncBuffer = new EditLogBuffer();
	
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
	public void flush() {
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
	 * editlog缓冲区
	 * @author yue
	 *
	 */
	class EditLogBuffer {
		
		/*
		 * 
		 */
		ByteArrayOutputStream out = new ByteArrayOutputStream(EDIT_LOG_BUFFER_LIMIT * 2);
		
		
		/**
		 * editlog写入缓冲区
		 */
		public void write(EditLog log)throws IOException{
			out.write(log.getContent().getBytes());
			System.out.println("在currentBuffer中写入一条数据 : "+log.getContent());
			System.out.println("当前缓冲区的大小 : "+this.size());
		}
		
		
		/**
		 * 获取当前缓冲区已经写入数据的字节数量
		 * @return
		 */
		public Integer size() {
			return out.size();
		}
		
		
		public void flush(){
			
		}
		
		public void clear(){
			
		}
		
	}
	
}