package juc.thread.executor1;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年11月1日 下午11:45:32
 *
 */
public interface ExecutorServer extends Executor{
	
	public <T> Future<T> submit(Runnable runnable);
	
	
	public <T> Future<T> submit(Callable callable);

}
