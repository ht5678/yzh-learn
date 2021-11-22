package disruptor.advanced.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import disruptor.advanced.common.MultiConsumer;
import disruptor.advanced.common.Trade;
import disruptor.advanced.common.TradeEventProducer;
import disruptor.advanced.common.TradePushlisher;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月22日上午11:17:38
 */
public class BatchDemo {

	
	
	
	public static void main(String[] args) throws Exception {
        //构建一个线程池用于提交任务
        ExecutorService es1 = Executors.newFixedThreadPool(1);
        ExecutorService es2 = Executors.newFixedThreadPool(5);
        //1 构建Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024*1024,
                es2,
                ProducerType.SINGLE,	//	Use ProducerType#SINGLE to create a SingleProducerSequencer; use ProducerType#MULTI to create a MultiProducerSequence
                new BusySpinWaitStrategy());        
        
        //2 把消费者设置到Disruptor中 handleEventsWith      
        // // 创建10个消费者来处理同一个生产者发的消息(这10个消费者不重复消费消息)
        MultiConsumer[] consumers = new MultiConsumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new MultiConsumer();
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        disruptor.start();
        
        
        
        TradeEventProducer producer = new TradeEventProducer(disruptor.getRingBuffer());

		//模拟消息发送
		for(int i = 0 ; i < 10000 ; i ++) {
			producer.onData(String.format("msg-%s", i));
		}
        
		
        //3 启动disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();
        
        CountDownLatch latch = new CountDownLatch(1);
        
        long begin = System.currentTimeMillis();
        
        es1.submit(new TradePushlisher(latch, disruptor));
        
        latch.await();  //进行向下
        
        disruptor.shutdown();
        es1.shutdown();
        es2.shutdown();
        System.err.println("总耗时: " + (System.currentTimeMillis() - begin));
	}
	
}
