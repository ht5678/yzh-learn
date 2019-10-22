package juc.thread.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月22日 下午11:42:13
 *
 */
public class ThreadPoolTest implements Runnable {
	
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		new Thread(new MonitorThreadPoolUtil((ThreadPoolExecutor)executorService, 1)).start();
		
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("悟空是只猴子");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		executorService.shutdown();
	}
	

	@Override
	public void run() {
		
	}

}
