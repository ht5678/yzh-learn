package juc.thread.lock.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月24日 下午10:21:49
 *
 */
public class CountDownLatch01 {

  private final static int threadCount = 100;

  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
    for (int i = 0; i < threadCount; i++) {
      final int threadNum = i;
      exec.execute(new Runnable() {
		
		@Override
		public void run() {
			try {
		          test(threadNum);
		        } catch (Exception e) {
		          e.printStackTrace();
		        } finally {
		          countDownLatch.countDown();
		        }
		}
	});
    }
//    countDownLatch.await();
    countDownLatch.await(120, TimeUnit.MILLISECONDS);
    System.out.println("结束");
    exec.shutdown();
  }

  private static void test(int threadNum) throws Exception {
    Thread.sleep(50);
    System.out.println(threadNum);
    Thread.sleep(50);
  }
}

