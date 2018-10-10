package miaosha.mq.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import miaosha.mq.dao.TicketDao;
import miaosha.mq.model.TicketInfo;
import miaosha.mq.utils.MqUtil;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:15:13
 *
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;
	
	
	/**
	 * 初始化
	 */
	public TicketServiceImpl(){
		try {
			MqUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 使用mq增强抢票业务
	 * 
	 * 
	 * 监听指定的topic.idcard队列,当此队列有数据时,数据会被取走
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicketV2(String ticketid){
		
//		TicketInfo ticketInfo = null;
		try{
			
			Consumer consumer = new DefaultConsumer(MqUtil.getConnection()){
	
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
						throws IOException {
					
					//msg就是接收到的idcard
					String msg = new String(body , "UTF-8");
					System.out.println("接收到消息: "+msg);
					
					//业务逻辑
					TicketInfo ticketInfo = null;
					System.out.println("出票成功");
					ticketInfo = ticketDao.getTicket(msg);
					
					MqUtil.sendTopic("exchange", "topic.ticketRespInfo", ticketInfo.getTicketid()+"~"+ticketInfo.getTicketstatus()
								+"~"+ticketInfo.getTicketmonney()+"~"+ticketInfo.getBuytime());
					
				}
				
			};
		
		
			MqUtil.getConnection().basicConsume("topic.ticket", consumer);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicket(String ticketid){
		return ticketDao.getTicket(ticketid);
	}
	
}
