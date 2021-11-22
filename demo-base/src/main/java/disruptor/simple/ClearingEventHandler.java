package disruptor.simple;

import com.lmax.disruptor.EventHandler;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月22日下午2:30:01 
 * @param <T>
 */
public class ClearingEventHandler<T> implements EventHandler<LogEvent>{
	
	
	/**
	 * 
	 */
    public void onEvent(LogEvent event, long sequence, boolean endOfBatch){
        event.clear(); 
    }
}