package juc.thread.executor1;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年11月1日 下午10:50:53
 *
 */
public class TestDemo {

	
	public static void main(String[] args) throws Exception{
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, new LinkedBlockingQueue<Runnable>(),true,1000);
		
		//记住要添加shutdown方法, 不然会一直运行不停止/
		for(int i = 0 ; i < 10 ; i++){
			
			//没有返回值
//			threadPoolExecutor.execute(new Runnable() {
//			threadPoolExecutor.submit(new Runnable() {
//				
//				@Override
//				public void run() {
//					System.out.println("测试自己实现的threadpoolexecutor");
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			});
			
			
			
			//有返回值
			Future<Object> future = threadPoolExecutor.submit(new Callable() {

				@Override
				public Object call() throws Exception {
					return "有返回值的threadpoolexecutor测试";
				}
			});
			System.out.println(future.get());
			
			
		}
	}
	
	
}
