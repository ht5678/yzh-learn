package juc.thread.executor1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年11月1日 下午10:43:08
 *
 */
public class ThreadPoolExecutor extends AbstractExecutorService{
	
	private volatile int corePoolSize;
	private volatile int maximumPoolSize;
	private volatile long keepAliveTime;
	private volatile boolean allowCoreThreadTimeOut;//描述是否需要超时
	
	private BlockingQueue<Runnable> workQueue;
	private final AtomicInteger ctl = new AtomicInteger(0);
	
	
	public ThreadPoolExecutor(int corePoolSize , int maxiumumPoolSize , BlockingQueue<Runnable> workQueue,boolean allowCoreThreadTimeOut,long keepAliveTime){
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maxiumumPoolSize;
		this.workQueue = workQueue;
		this.keepAliveTime = keepAliveTime;
		if(keepAliveTime>0){
			this.allowCoreThreadTimeOut=true;
		}
		this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
	}
	
	
	
	/**
	 * 暴露的接口,接受task任务的
	 */
	@Override
	public void execute(Runnable command) {
		if(command==null){
			throw new NullPointerException();
		}
		int c = ctl.get();
		if(c<corePoolSize){
			addWorker(command,true);
		}else if(workQueue.offer(command)){
			//这里应该有判断, 小于核心数,addWork方法,大于核心数,offer到队列里边
			addWorker(null, false);
		}else{
			reject(command);
		}
	}
	
	
	class RejectedExecutionHandler{
		public void rejectdExecution(Runnable command){
			throw new RejectedExecutionException("这个task"+command+" , 我处理不过来了!");
		}
	}
	
	private void reject(Runnable command){
		RejectedExecutionHandler handler = new RejectedExecutionHandler();
		handler.rejectdExecution(command);
	}
	
	
	private void addWorker(Runnable task , boolean core){
		if(core){
			ctl.incrementAndGet();
		}
		Worker worker = new Worker(task);
		worker.thread.start();
	}
	

	
	/**
	 * 
	 * 
	 * 
	 * @author yuezh2   2019年11月1日 下午10:53:32
	 *
	 */
	class Worker extends ReentrantLock implements Runnable{
		
		private Runnable firstTask;
		private Thread thread;
		
		
		public Worker(Runnable firstTask) {
			this.firstTask = firstTask;
			thread = new Thread(this);
		}
		

		@Override
		public void run() {
			runWorker(this);
		}
		
		
		
		private void runWorker(Worker w){
			try{
				w.lock();
				Runnable task = w.firstTask;
				if(task!=null || (task = getTask()) != null){
					task.run();
				}
			}finally {
				processWorkerExit(w);
				w.unlock();
			}
		}
		
		
		/**
		 * todo
		 * @param w
		 */
		private void processWorkerExit(Worker w){
			
		}
		
		
		public Runnable getTask(){
			try{
				if(workQueue.isEmpty()){
					return null;
				}
				Runnable r = allowCoreThreadTimeOut?workQueue.poll(keepAliveTime,TimeUnit.NANOSECONDS):workQueue.take();
				System.out.println(workQueue.size());
				if(r!=null){
					return r;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		
	}
	
	
}
