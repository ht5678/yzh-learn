package disruptor.simple;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 组装 Disruptor
 * @author yuezh2@lenovo.com
 *	@date 2021年11月18日下午9:06:58
 */
public class LogEventMain {

	
	/**
	 * Disruptor 构造器中的两个参数-生产者类型 ProducerType（单个，或者多个？），
	 * WaitStrategy（等待RingBuffer中对应序列号可用的策略）会影响 Disruptor 的性能。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LogEventFactory factory = new LogEventFactory();
		
		
		//环形数组的容量 . 必须是2的倍数
		int bufferSize = 1024;
		
		//构造Disruptor
		Disruptor<LogEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE , ProducerType.SINGLE , 
									new YieldingWaitStrategy());
		
		//设置消费者
		disruptor.handleEventsWith(new LogEventConsumer());
		
		//启动Disruptor
		disruptor.start();
		
		//生产者要使用Disruptor的环形结构
		RingBuffer<LogEvent> ringBuffer = disruptor.getRingBuffer();
		
		LogEventProducer producer = new LogEventProducer(ringBuffer);
		
		//模拟消息发送
		for(int i = 0 ; i < 10000 ; i ++) {
			producer.onData(String.format("msg-%s", i));
		}
		
		
	}
	
}
