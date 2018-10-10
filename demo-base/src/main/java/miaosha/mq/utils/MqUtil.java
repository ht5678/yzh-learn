package miaosha.mq.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月29日 下午4:39:22
 *
 */
public class MqUtil {
	
	
	private static Channel channel = null;
	
	private static Connection conn = null;
	
	//springboot消息队列模板
//	@Autowired
//	private AmqpTemplate template;
	
	/**
	 * 将数据发送到交换机和队列
	 * @param exchange
	 * @param routeKey
	 * @param content
	 */
	public static void sendTopic(String exchange , String routeKey,String content){
		try{
		channel.queueDeclare(routeKey, false, false, false, null);
		channel.basicPublish(exchange, routeKey, null, content.getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 关闭mq连接
	 */
	public static void closeConnection(){
		try{
			if(channel != null){
				channel.close();
			}
			if(conn!=null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取mq连接
	 * @return
	 * @throws Exception
	 */
	public static Channel getConnection()throws Exception{
		try{
			
			if(channel!=null && channel.isOpen()){
				return channel;
			}
			
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("10.99.205.17");
			factory.setPort(5672);
			factory.setUsername("admin");//用户名
			factory.setPassword("p@ssw0rd");//密码
			
			conn = factory.newConnection();
			channel = conn.createChannel();
			
			
	//		String queueName = "hello";
	//		channel.queueDeclare(queueName, false, false, false, null);
	//		
	//		String message = "Hello World";
	//		channel.basicPublish("", queueName, null, message.getBytes());
	//		channel.close();
	//		conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return channel;
	}
	
	
}
