package disruptor.advanced.common;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * 消费者1：重置Event的name
 * 
 * 消费者既可以通过实现EventHandler成为消费者，也可以通过实现WorkHandler成为消费者；
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:21:12
 */
public class Handler1 implements EventHandler<Trade>,WorkHandler<Trade>{

	@Override
	public void onEvent(Trade event) throws Exception {
		this.onEvent(event);
	}

	@Override
	public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
		System.err.println("handler1 : SET NAME");
		event.setName("H1");
	}

}
