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
	public static void main(String[] args) throws Exception{
		LogEventFactory factory = new LogEventFactory();
		
		
		//环形数组的容量 . 必须是2的倍数
		int bufferSize = 1024;
		
		
		//com.lmax.disruptor.BlockingWaitStrategy		default
		//最低效的策略，但其对CPU的消耗最小，并且在各种部署环境中能提供更加一致的性能表现；
		//内部维护了一个重入锁ReentrantLock和Condition；
		
		//com.lmax.disruptor.SleepingWaitStrategy
		//性能表现和com.lmax.disruptor.BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
		//是一种无锁的方式；
		
		//com.lmax.disruptor.YieldingWaitStrategy
		//性能最好，适合用于低延迟的系统；在要求极高性能且事件处理线程数小于CPU逻辑核心树的场景中，推荐使用此策略；例如，CPU开启超线程的特性；
		//也是无锁的实现，只要是无锁的实现，signalAllWhenBlocking()都是空实现；
		
		//构造Disruptor
		Disruptor<LogEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE , ProducerType.SINGLE , 
									new YieldingWaitStrategy());//决定一个消费者如何等待生产者将Event置入Disruptor；	其所有实现都是针对消费者线程的；
		
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
		
		Thread.sleep(1000);
	}
	
}
