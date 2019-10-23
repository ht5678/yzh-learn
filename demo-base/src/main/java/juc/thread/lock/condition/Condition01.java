package juc.thread.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月23日 下午11:51:46
 *
 */
public class Condition01 {
  static Lock lock = new ReentrantLock();
  static Condition full = lock.newCondition();
  static Condition empty = lock.newCondition();
  private static   int i=0;
  public static void main(String[] args) {
      new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
	          Condition01.producer();
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
		}
	}).start();
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
		        Condition01.consumer();
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		}
	}).start();


  }

  /***
   * 生产者
   * @throws InterruptedException
   */
  public static void producer() throws InterruptedException {
    lock.lock();
    try {
      for (;;) {
          i++;
        System.out.println("增加:"+i);
          if(i>=30) {
            System.out.println("等待中...");
            Thread.sleep(1000);
            full.await();
            empty.signal();
          }
      }
    } finally {
      lock.unlock();
    }
  }

  /***
   * 消费者
   * @throws InterruptedException
   */
  public  static void consumer() throws InterruptedException {
    lock.lock();
    try {

      for (;;){
        i--;
        System.out.println("减去:"+i);
        if (i<=0){
          System.out.println("唤醒中...");
          Thread.sleep(1000);
          full.signal();
          empty.await();
        }
      }

    } finally {
      lock.unlock();
    }
  }
}
