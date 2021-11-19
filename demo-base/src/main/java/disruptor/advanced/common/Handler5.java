package disruptor.advanced.common;
import com.lmax.disruptor.EventHandler;
 

/**
 * 消费者5：原价格基础上加3；
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:25:49
 */
public class Handler5 implements EventHandler<Trade> {
 
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 5 : GET PRICE: " +  event.getPrice());
        Thread.sleep(1000);
        event.setPrice(event.getPrice() + 3.0);
    }
 
}