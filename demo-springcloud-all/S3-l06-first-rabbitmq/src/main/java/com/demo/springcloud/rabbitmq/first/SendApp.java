package com.demo.springcloud.rabbitmq.first;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
 * @author yuezh2   2018年8月31日 下午5:29:35
 *
 */
public class SendApp {
	
	
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
		
		String message = "Hello World";
		channel.basicPublish("", queueName, null, message.getBytes());
		channel.close();
		conn.close();
		
	}
	

}
