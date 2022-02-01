package org.dfs.namenode;

import java.util.LinkedList;

/**
 * 
 * editslog双缓冲机制 + 全局txid + 分段锁 
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:53:59
 */
public class FSEditlog {

	private Integer txidSeq =0;
	private ThreadLocal<Long> localTxid = new ThreadLocal<>();
	
	private volatile Boolean isSyncRunning = false;
	
	private volatile Long syncMaxTxId = 11L;
	
	private volatile Boolean isWatiSync = false;
	
	private DoubleBuffer editLogBuffer = new DoubleBuffer();
	
	/**
	 * 记录 edits log日志
	 * @param string
	 */
	public void logEdit(String content) {
		//这里必须直接加锁
		//分段加锁1
		synchronized (this) {
			//获取全剧唯一递增的txid , 代表了 edits log的序号
			txidSeq++;
			long txid = txidSeq;
			localTxid.set(txid);		//放到ThreadLocal里去 , 相当于维护了一份本地线程的副本
			
			//构造一条edits log对象
			EditLog log = new EditLog(txid,content);
			
			//将edits log写入内存缓冲中 , 不是直接输入磁盘文件
			editLogBuffer.write(log);
		}
		logSync();
	}
	
	
	/**
	 * 将内存缓冲中的数据输入磁盘文件中
	 * 在这里尝试允许一个线程一次性将内存中的数据刷入磁盘文件中
	 * 相当于实现一个批量将内存缓冲数据刷磁盘的过程
	 * 
	 */
	private void logSync() {
		//再次尝试加锁
		//分段加锁2
		synchronized (this) {
			//如果说当前正好有人刷内存缓冲到磁盘中去
			if(isSyncRunning) {
				//那么这里应该有一些逻辑判断
				
				//加入说某个线程已经把txid = 1,2,3,4,5 的edits log都从 syncBuffer刷入磁盘了
				//或者说此时正在刷入磁盘中
				//此时syncMaxTxid = 5 , 代表的是正在输入磁盘的最大txid
				//那么这个时候来一个线程 , 他对应的txid =3 ,此时他是直接返回了
				//就代表肯定是他对应的edits log已经被别的线程在刷入磁盘了
				//这个时候txid = 3的线程就不需要等待了
				long txid = localTxid.get(); //获取到本地线程的副本
				if(txid <= syncMaxTxId) {
					return;
				}
				
				//此时再来一个txid = 5的线程的话 , 那么就会发现 , 已经有现成在等待刷下一批数据到磁盘了
				//此时他会直接返回
				//假如说此时来一个txid = 6的线程 ,  那么不好说
				//他就需要做一些等待 ,同时要释放掉锁
				if(isWatiSync) {
					return;
				}
				
				//比如说此时可能是txid= 15的线程在这里等待
				isWatiSync = true;
				while(isSyncRunning) {
					try {
						wait(2000);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				isWatiSync = false;
			}
			
			//交换两块缓冲区
			editLogBuffer.setReadyToSync();
			//然后可以保存一下当前要同步到磁盘中区的最大txid
			//此时editLogBuffer中的syncBuffer这块区域 , 交换完以后这里可能有多条数据
			//而且他里面的edits log的txid一定是从小到大的
			//此时要同步的txid=6,7,8,9,10,11,12
			//syncMaxTxid = 12;
			syncMaxTxId = editLogBuffer.getSyncMaxTxid();
			//设置当前正在同步到磁盘的标志位
			isSyncRunning = true;
		}
		
		//开始同步内存缓冲的数据到磁盘文件里去
		//这个过程其实比较慢 , 基本上肯定是ms级别了 , 弄不好就要几十ms
		editLogBuffer.flush();
		
		
		//分段加锁3
		synchronized (this) {
			//同步完了磁盘之后 , 就会将标志位复位 , 再释放锁
			isSyncRunning = false;
			//唤醒可能正在等待他同步磁盘的线程
			notifyAll();
		}
	}

	
	
	
	
	
	/**
	 * 代表了一条 edits log
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:40:07
	 */
	class EditLog{
		private long txid;
		
		private String content;
		
		
		
		public EditLog(long txid , String content) {
			this.txid = txid;
			this.content = content;
		}
		
		

		public long getTxid() {
			return txid;
		}

		public void setTxid(long txid) {
			this.txid = txid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}



		@Override
		public String toString() {
			return "EditLog [txid=" + txid + ", content=" + content + "]";
		}
		
	}
	
	
	
	/**
	 * 内存双缓冲
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:40:58
	 */
	class DoubleBuffer{
		/*
		 * 是专门用来承载线程写入edits log
		 */
		private LinkedList<EditLog> currentBuffer = new LinkedList<>();
		/*
		 * 专门用来将数据同步到磁盘中去的一块缓冲
		 */
		private LinkedList<EditLog> syncBuffer = new LinkedList<>();
		
		/**
		 * 将edits log写到内存缓冲里去
		 */
		public void write(EditLog log) {
			currentBuffer.add(log);
		}
		
		/*
		 * 刷写磁盘
		 */
		public void flush() {
			for(EditLog log : syncBuffer) {
				//把数据写到磁盘上
				System.out.println("存入磁盘日志信息: "+log);
			}
			syncBuffer.clear();
		}

		/**
		 * 获取内存2里的日志的最大事务编号
		 * @return
		 */
		public Long getSyncMaxTxid() {
			return syncBuffer.getLast().txid;
		}

		
		/**
		 * 交换两块缓冲区,为了同步内存数据到磁盘做准备
		 */
		public void setReadyToSync() {
			LinkedList<EditLog> tmp = currentBuffer;
			currentBuffer = syncBuffer;
			syncBuffer = tmp;
		}
		
	}
	
}
