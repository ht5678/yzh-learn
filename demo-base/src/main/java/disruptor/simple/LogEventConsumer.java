package disruptor.simple;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;

/**
 * 定义消费者来处理我们的事件。
 * @author yuezh2@lenovo.com
 *	@date 2021年11月18日下午9:01:59
 */
public class LogEventConsumer implements EventHandler<LogEvent>,LifecycleAware{

	
	
	
	@Override
	public void onEvent(LogEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println(Thread.currentThread().getName()+" | Event : "+event);
		event.clear();
	}

	@Override
	public void onStart() {
		System.out.println("创建了 ...");
	}

	@Override
	public void onShutdown() {
		System.out.println("关闭了 ...");
	}

	
	
}
