package juc.thread.lock.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 
 * 
 * 可以用来限流 , redis+lua(分布式限流)限流效果不如直接用semaphore(jvm级别限流)
 * 因为分布式的时候 , 每台机器性能不一样 ,  redis+lua需要每台机器都要配置.
 * 
 * @author yuezh2   2019年10月24日 下午11:03:37
 *
 */
public class Semaphore01 {
  private static Semaphore semaphore=new Semaphore(5);

  public static void main(String[] args) {
       for (int i=0;i<20;i++){
         final  int j=i;
         new  Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
		           action(j);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
			}
		}).start();

       }
  }


  public static void action(int i) throws InterruptedException {
      semaphore.acquire();
    System.out.println(i+"在京东秒杀iphonex");
/*    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
    System.out.println(i+"秒杀成功");
    semaphore.release();


  }
}

