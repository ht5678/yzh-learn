package juc.thread.executor1;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年11月1日 下午11:46:51
 *
 */
public abstract class AbstractExecutorService  implements ExecutorServer{

	@Override
	public <T> Future<T> submit(Runnable runnable) {
		FutureTask futureTask = new FutureTask<T>(runnable , null);
		execute(futureTask);
		return futureTask;
	}

	@Override
	public <T> Future<T> submit(Callable callable) {
		FutureTask futureTask = new FutureTask<T>(callable);
		execute(futureTask);
		return futureTask;
	}

	
	
}
