package disruptor.advanced.common;

import com.lmax.disruptor.WorkHandler;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月22日下午2:53:28
 */
public class MultiConsumer  implements WorkHandler<Trade>{

	
	
	@Override
	public void onEvent(Trade event) throws Exception {
		System.out.println(Thread.currentThread().getName()+"  消费者消费了消息 :  " +event.toString());
	}

	
}
