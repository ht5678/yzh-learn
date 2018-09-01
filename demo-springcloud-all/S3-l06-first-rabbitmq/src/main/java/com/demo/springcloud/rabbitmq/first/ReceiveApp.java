package com.demo.springcloud.rabbitmq.first;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年9月1日 下午7:17:30
 *
 */
public class ReceiveApp {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.99.205.17");
		factory.setPort(5672);
		factory.setUsername("admin");//用户名
		factory.setPassword("p@ssw0rd");//密码
		
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		String queueName = "hello";
		channel.queueDeclare(queueName, false, false, false, null);
		
		Consumer consumer = new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				
				String msg = new String(body , "UTF-8");
				System.out.println("接收到消息: "+msg);
				
			}
			
		};
		
		
		channel.basicConsume(queueName, consumer);
		
	}
	
}
