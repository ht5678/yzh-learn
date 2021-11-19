package disruptor.advanced.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import disruptor.advanced.common.Handler1;
import disruptor.advanced.common.Handler2;
import disruptor.advanced.common.Handler3;
import disruptor.advanced.common.Trade;
import disruptor.advanced.common.TradePushlisher;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午7:19:16
 */
public class ConcurrentDemoMain {

	

    @SuppressWarnings("unchecked")
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
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());        
        
        //2 把消费者设置到Disruptor中 handleEventsWith      
        //2.2 并行操作: 可以有两种方式去进行
        //1 handleEventsWith方法 添加多个handler实现即可
        //2 handleEventsWith方法 分别进行调用
        disruptor.handleEventsWith(new Handler1(), new Handler2(), new Handler3());
 //     disruptor.handleEventsWith(new Handler1());
//      disruptor.handleEventsWith(new Handler2());
//      disruptor.handleEventsWith(new Handler3());
        
        //3
        disruptor
//        				.after(new Handler3(),new Handler2())
        				.handleEventsWith(new Handler1())
//        				.then(new Handler2())
//        				.and(new Handler2())
        				;
        
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
