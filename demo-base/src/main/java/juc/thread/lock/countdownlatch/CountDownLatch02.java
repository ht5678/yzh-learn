package juc.thread.lock.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 * countdownlatch模拟future执行完线程返回结果
 * 等待异步处理结果之后才进行我的操作
 * 
 * @author yuezh2   2019年10月24日 下午10:23:24
 *
 */
public class CountDownLatch02 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CountDownLatch countDownLatch=new CountDownLatch(1);
    CallableImple callableImple=new CallableImple(countDownLatch);
    FutureTask<String> futureTask = new FutureTask<String>(callableImple);
    new Thread(futureTask).start();
    if (!futureTask.isDone()){
      try {
        System.out.println("你知道我一直在等你嘛>>>>>>>>");
        countDownLatch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(futureTask.get());


  }
  
}