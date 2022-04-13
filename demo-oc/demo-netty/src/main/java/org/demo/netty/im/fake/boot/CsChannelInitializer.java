package org.demo.netty.im.fake.boot;

import org.demo.netty.im.fake.exception.ExceptionListener;
import org.demo.netty.im.fake.im.cs.config.CsConfiguration;
import org.demo.netty.im.fake.util.JsonSupport;
import org.demo.netty.im.fake.util.PacketDecoder;
import org.demo.netty.im.fake.util.PacketEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yue
 *
 */
public class CsChannelInitializer extends ChannelInitializer<Channel>{

	private CsConfiguration serverConfig;
	private JsonSupport jsonSupport;
	
	private PacketEncoder encoder;
	private PacketDecoder decoder;
	
	private EncoderHandler encoderHandler;
	
	private SslContext sslContext;
	private SASLAuthentication saslAuthentication;
	
	private ExceptionListener exceptionListener;
	
	/**
	 * 
	 * @param serverConfig
	 */
	public CsChannelInitializer(CsConfiguration serverConfig){
		this.serverConfig = serverConfig;
		init();
	}
	
	
	/**
	 * 
	 */
	public void init(){
		jsonSupport = serverConfig.getJsonSupport();
		encoder = new PacketEncoder(true, jsonSupport);
		decoder = new PacketDecoder(jsonSupport); 
		encoderHandler = new EncoderHandler(encoder);
		sslContext = null;
		
		saslAuthentication = new SASLAuthentication(serverConfig);
		
		
	}
	
	
	@Override
	protected void initChannel(Channel arg0) throws Exception {
		
	}

}
