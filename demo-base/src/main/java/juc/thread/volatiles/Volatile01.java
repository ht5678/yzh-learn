package juc.thread.volatiles;



/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月23日 下午11:04:29
 *
 */
public class Volatile01 {

  volatile boolean stop =false;


  public void shutDown() {
    stop = true;
  }

  public void doWork() {
    while (!stop) {
    }
    System.out.println("你能读到我吗...");
  }


  public static void main(String[] args) throws InterruptedException {
    final Volatile01 volatile01=new Volatile01();
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			volatile01.doWork();//先就开始工作，stop为false
		}
	}).start();
    Thread.sleep(1000);
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			volatile01.doWork();//先就开始工作，stop为false
		}
	}).start();

  }
}