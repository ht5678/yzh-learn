package disruptor.simple;

import com.lmax.disruptor.EventFactory;

/**
 * 定义事件，事件工厂
 * @author yuezh2@lenovo.com
 *	@date 2021年11月18日下午9:00:21
 */
public class LogEventFactory implements EventFactory<LogEvent>{

	
	
	/**
	 * 事件工厂用于 Disruptor 在 RingBuffer 中预分配空间，从 RingBuffer 的源码可以看到这一点。
	 * 
	 * private void fill(EventFactory<E> eventFactory)
		{
		    for (int i = 0; i < bufferSize; i++)
		    {
		        entries[BUFFER_PAD + i] = eventFactory.newInstance();
		    }
		}
	 * 
	 */
	@Override
	public LogEvent newInstance() {
		return new LogEvent();
	}

	
	
}
