package disruptor.advanced.common;

import java.util.Random;

import com.lmax.disruptor.EventTranslator;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:13:31
 */
public class TradeEventTranslator implements EventTranslator<Trade>{

	private Random random = new Random();
	
	
	@Override
	public void translateTo(Trade event, long sequence) {
		this.generateTrade(event);
	}
	
	
	private void generateTrade(Trade event) {
		event.setPrice(random.nextDouble() * 9999);
	}
	
	
}
