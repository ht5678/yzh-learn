package netty.io.demo.udt.rendezvousBytes;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * 
 * UDT Byte Stream Peer
 * <p/>
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 * <p/>
 * 
 * @author yuezh2   2016年11月21日 下午5:04:27
 *
 */
public class ByteEchoPeerBase {
	
	
	protected final int messageSize;
	protected final SocketAddress myAddress;
	protected final SocketAddress peerAddress;
	
	
	
	
	public ByteEchoPeerBase(int messageSize , SocketAddress myAddress , SocketAddress peerAddress){
		this.messageSize = messageSize;
		this.myAddress = myAddress;
		this.peerAddress = peerAddress;
	}
	
	
	
	
	
	public void run() throws Exception{
		final ThreadFactory connectFactory = new DefaultThreadFactory("rendezvous");
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1 , 
				connectFactory , NioUdtProvider.BYTE_PROVIDER);
		
		try{
			final Bootstrap b = new Bootstrap();
			b.group(connectGroup)
			  .channelFactory(NioUdtProvider.BYTE_RENDEZVOUS)
			  .handler(new ChannelInitializer<UdtChannel>() {

				@Override
				protected void initChannel(UdtChannel ch) throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO)
							,new ByteEchoPeerHandler(messageSize));
				}
				  
			});
			
			final ChannelFuture future = b.connect(peerAddress, myAddress).sync();
			future.channel().closeFuture().sync();
			
		}finally{
			connectGroup.shutdownGracefully();
		}
	}
	
	

}
