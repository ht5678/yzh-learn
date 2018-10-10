package miaosha.mq;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import miaosha.mq.utils.MqUtil;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月27日 下午9:17:15
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestRabbitMqMiaosha2 {
	
	
	CloseableHttpClient client = HttpClients.createDefault();
	
	private static final int num = 200;
	
	private static int count = 1;
	
	private final String url = "http://localhost:8080/demo-base/mq/buyTicket";
	
	private static CountDownLatch cdl = new CountDownLatch(num);
	
	
	/**
	 * 
	 */
	@BeforeClass
	public void init()throws Exception{
		MqUtil.getConnection();
	}
	
	
	@AfterClass
	public void destroy(){
		MqUtil.closeConnection();
	}
	
	
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testInvokeRemote()throws InterruptedException{
		for(int i = 0 ; i < num ; i ++){
			System.out.println("test"+i);
			new Thread(new TicketQuest()).start();
			cdl.countDown();
		}
		Thread.sleep(3000);
	}
	
	
	
	
	/**
	 * 内部类继承线程接口,  用于模拟用户买票请求
	 * 
	 * 
	 * @author yuezh2   2018年9月27日 下午9:20:28
	 *
	 */
	public class TicketQuest implements Runnable{

		@Override
		public void run() {
			
			try {
				cdl.await();
			
				
				
				//逻辑
				String exchange = "exchange";		//交换器
				String routeKey = "topic.ticket";		//路由键
				String idcard = "123456";
				MqUtil.sendTopic(exchange, routeKey, idcard);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
