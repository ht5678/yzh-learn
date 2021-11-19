package disruptor.advanced.common;

import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:11:13
 */
public class TradePushlisher implements Runnable{

	private Disruptor<Trade> disruptor;
	private CountDownLatch latch;
	
	private static int PUBLISH_COUNT = 1;
	
	/**
	 * 
	 */
	public TradePushlisher(CountDownLatch latch , Disruptor<Trade> disruptor) {
		this.disruptor  = disruptor;
		this.latch = latch;
	}
	
	
	@Override
	public void run() {
		TradeEventTranslator eventTranslator = new TradeEventTranslator();
		for(int i = 0 ; i < PUBLISH_COUNT ; i++) {
			//新的提交任务方式
			disruptor.publishEvent(eventTranslator);
		}
		latch.countDown();
	}

}
