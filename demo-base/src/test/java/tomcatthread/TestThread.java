package tomcatthread;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;
import org.junit.Test;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年6月18日 下午4:47:14  
 *
 */
public class TestThread {
	
	
	private static final int COUNT=1000;
	

	@Test
	public void test() throws Exception{
		for(int i =0 ; i < COUNT ; i++){
			Thread t = new Thread(new ExecuteThread(3L));
			t.start();
			latch.countDown();
		}
		
		Thread.currentThread().sleep(10000);
		System.out.println("total : "+total);
	}
	
	
	
	
	private CountDownLatch latch = new CountDownLatch(COUNT);
	//总共卖出去多少
	private Long total = 0L;
	
	private class ExecuteThread implements Runnable{
		
		private Long amount;
		
		public ExecuteThread(Long amount){
			this.amount = amount;
		}
		
		public void run() {
			byte[] bytes = null;
			try {
				latch.await();

				bytes = IOUtils.toByteArray(URI.create("http://host:8088/proxy/test/delay?times=5000"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(new String(bytes));
			if(bytes!=null){
				synchronized (total) {
					total += amount;
				}
			}
		}
	}
	
}
