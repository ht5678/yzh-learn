package disruptor.advanced.common;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
 

/**
 * 消费者2：设置ID
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:24:43
 */
public class Handler2 implements EventHandler<Trade> {
 
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 2 : SET ID");
        Thread.sleep(2000);
        event.setId(UUID.randomUUID().toString());
    }
 
}
