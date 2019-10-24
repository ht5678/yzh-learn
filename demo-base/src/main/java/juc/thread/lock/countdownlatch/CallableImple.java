package juc.thread.lock.countdownlatch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月24日 下午10:21:28
 *
 */
public class CallableImple implements Callable  {
  private CountDownLatch latch;
  public CallableImple(CountDownLatch latch) {
    this.latch=latch;
  }
  public String doSomeThing(){
    latch.countDown();
    return "一分钟就干完了";
  }


  @Override
  public Object call() throws Exception {
    Thread.sleep(1000);
    return doSomeThing();
  }
}
