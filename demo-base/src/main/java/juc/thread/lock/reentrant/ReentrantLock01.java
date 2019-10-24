package juc.thread.lock.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月23日 下午11:27:36
 *
 */
public class ReentrantLock01 {
  private static final ReentrantLock reentrantLock=new ReentrantLock();
   static int i=0;

  public static void main(String[] args) throws InterruptedException {
    final ReentrantLock01 reentrantLock01 = new ReentrantLock01();
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			reentrantLock01.add();
		}
	}).start();
    Thread.sleep(1000);//主要目的是让两个线程把事情干完
    
    
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			reentrantLock01.add();
		}
	}).start();
    Thread.sleep(1000);//主要目的是让两个线程把事情干完
    System.out.println(i);

  }

  /***
   * 强调try fianlly规范
   */
  public  void add(){
    try {
      reentrantLock.lock();
      for (int j = 0; j < 10000; j++) {
        i++;
      }
    } finally {
      reentrantLock.unlock();
    }
  }





}
