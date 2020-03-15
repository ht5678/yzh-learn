package netty.learn.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年3月15日 下午3:39:01  
 *
 */
public class EchoClient {

	
	public static void main(String[] args)throws Exception {
		int port = 8080;
		if(args!=null && args.length>0) {
			port = Integer.valueOf(args[0]);
		}
		
		new EchoClient().connect(port, "localhost");
	}
	
	
	
	
	public void connect(int port , String host)throws Exception{
		//工作线程组,老板线程组会把任务丢给他,让手下线程组去做任务,服务客户
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
								.option(ChannelOption.TCP_NODELAY, true)
								.handler(new ChannelInitializer<SocketChannel>() {

									@Override
									protected void initChannel(SocketChannel ch) throws Exception {
										ch.pipeline().addLast(new EchoClientHandler());
									}
									
								});
			
			//发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();
			
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
			
		}finally {
			//优雅退出,释放线程池资源
			group.shutdownGracefully();
		}
	}
	
	
	
}
