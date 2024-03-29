package org.demo.im.gateway.tcp.push;

import org.demo.im.gateway.tcp.ClientManager;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

/**
 * 消息推送管理组件
 * @author yue
 *
 */
public class PushManager {

	
	/**
	 * 启动消息管理组件
	 */
	public void start() {
		new PushThread().start();
	}
	
	
	
	/**
	 * 消息推送线程
	 * @author yue
	 */
	class PushThread extends Thread{
		@Override
		public void run() {
			try{
				Thread.sleep(60 * 1000);
				
				String testUserId = "test002";
				
				ClientManager clientManager = ClientManager.getInstance();
//				SocketChannel socketChannel = clientManager.getChannel(testUserId);
				SocketChannel socketChannel = clientManager.getClient(testUserId);
				
				if(socketChannel != null){
					byte[] messageBytes = "test001发送过来的消息 $_".getBytes();
					ByteBuf messageBuffer = Unpooled.buffer(messageBytes.length);
					messageBuffer.writeBytes(messageBytes);
					socketChannel.writeAndFlush(messageBuffer);
					
					System.out.println("给客户端反向推送消息: userId = "+testUserId + " , socketChannel="+socketChannel);
				}else{
					System.out.println("目标用户已经下线 , 等待其上线后再推送");
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
