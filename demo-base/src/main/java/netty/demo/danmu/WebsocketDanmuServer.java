package netty.demo.danmu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年6月15日 下午2:41:33  
 *
 */
public class WebsocketDanmuServer {

	private int port;
	
	
	public WebsocketDanmuServer(int port) {
		this.port = port;
	}
	
	
	
	public void run() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup(2);//1
		EventLoopGroup workerGroup = new NioEventLoopGroup(3);
		
		try {
			ServerBootstrap b = new ServerBootstrap();//2
			b.group(bossGroup,workerGroup)
			  .channel(NioServerSocketChannel.class)
			  .childHandler(new WebsocketDanmuServerInitializer())
			  .option(ChannelOption.SO_BACKLOG, 128)
			  .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			System.out.println("server启动了"+port);
			
			//绑定端口,开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			
			//等待服务器socket关闭
			//在这个例子中,这不会发生,但你可以优雅的关闭服务器
			f.channel().closeFuture().sync();
			
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		System.out.println("server 关闭了");
	}
	
	
	
	public static void main(String[] args) throws Exception{
		int port ;
		if(args.length>0) {
			port = Integer.parseInt(args[0]);
		}else {
			port = 8080;
		}
		
		new WebsocketDanmuServer(port).run();
	}
	
	
}
