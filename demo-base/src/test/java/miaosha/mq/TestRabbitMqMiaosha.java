package miaosha.mq;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月27日 下午9:17:15
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestRabbitMqMiaosha {
	
	
	CloseableHttpClient client = HttpClients.createDefault();
	
	private static final int num = 200;
	
	private static int count = 1;
	
	private final String url = "http://localhost:8080/demo-base/mq/buyTicket";
	
	private static CountDownLatch cdl = new CountDownLatch(num);
	
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testInvokeRemote()throws InterruptedException{
		for(int i = 0 ; i < num ; i ++){
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
			
				HttpPost post = new HttpPost(url);
				HttpResponse response = client.execute(post);
				
				String result = EntityUtils.toString(response.getEntity());
				if(StringUtils.isNotEmpty(result) && result.contains("buytime")){
					System.out.println(++count);
				}
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
