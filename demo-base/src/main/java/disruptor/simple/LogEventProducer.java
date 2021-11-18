package disruptor.simple;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 定义事件的源头，里面的事件转换器（EventTranslatorOneArg）会把输出的参数转为我们的事件类型。
 * @author yuezh2@lenovo.com
 *	@date 2021年11月18日下午9:03:18
 */
public class LogEventProducer {
	
	private final RingBuffer<LogEvent> ringBuffer;
	
	/**
	 * 
	 * @param ringBuffer
	 */
	public LogEventProducer(RingBuffer<LogEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	
	private static final EventTranslatorOneArg<LogEvent, String> TRANSLATOR = new EventTranslatorOneArg<LogEvent, String>() {

		@Override
		public void translateTo(LogEvent event, long sequence, String msg) {
			event.setMsg(msg);
		}
		
	};

	
	
	public void onData(String msg) {
		ringBuffer.publishEvent(TRANSLATOR , msg);
	}
	
}
