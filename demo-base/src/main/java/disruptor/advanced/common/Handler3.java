package disruptor.advanced.common;

import com.lmax.disruptor.EventHandler;
 

/**
 * 消费者3：输出Event信息
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:25:11
 */
public class Handler3 implements EventHandler<Trade> {
 
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 3 : NAME: " 
                                + event.getName() 
                                + ", ID: " 
                                + event.getId()
                                + ", PRICE: " 
                                + event.getPrice()
                                + " INSTANCE : " + event.toString());
    }
 
}

