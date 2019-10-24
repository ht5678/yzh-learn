package juc.thread.lock.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * countdownlatch模拟join
 * 
 * @author yuezh2   2019年10月24日 下午11:01:10
 *
 */
public class CountDownLatchJoin {
  static CountDownLatch c = new CountDownLatch(1);
  public static void main(String[] args) throws InterruptedException {
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
	          System.out.println("我在干活");
	          Thread.sleep(2000);
	          c.countDown();
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
			
		}
	}).start();
    c.await();
    System.out.println("我等你干完");
  }
}