package disruptor.advanced.common;
import com.lmax.disruptor.EventHandler;
 

/**
 * 消费者4：设置Event价格；
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:25:38
 */
public class Handler4 implements EventHandler<Trade> {
 
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17.0);
    }
 
}