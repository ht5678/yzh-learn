package org.demo.netty.im.initializer;

import org.demo.netty.im.bs.config.Configuration;
import org.demo.netty.im.bs.handler.AuthorizationHandler;
import org.demo.netty.im.bs.handler.PollingTransport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:20:48
 */
public class BsChannelInitializer extends ChannelInitializer<Channel>{

	public static final String SOCKET_TO_ENCODER = "socketIoEncoder";
	public static final String WEB_SOCKET_TRANSPORT_COMPRESSION = "webSocketTransportCompression";
	
	public static final String AUTHORIZE_HANDLER = "authorizeHandler";
	public static final String WEB_SOCKET_TRANSPORT = "webSocketTransport";
	public static final String WEB_SOCKET_AGGREGATOR = "webSocketAggregator";
	public static final String XHR_POLLING_TRANSPORT = "xhrPollingTransport";
	
	public static final String HTTP_AGGREGATOR =  "httpAggregator";
	public static final String HTTP_REQUEST_DECODER = "httpDecoder";
	public static final String HTTP_ENCODER = "httpEncoder";
	public static final String HTTP_COMPRESSION = "httpCompression";
	public static final String WRONG_URL_HANDLER = "wrongUrlBlocker";
	
	
	public static final String SSL_HANDLER = "ssl";
	
	private Configuration config;
	private SslContext sslContext;
	
	private AuthorizationHandler authorizeHandler;
	
	private PollingTransport xhrPollingTransport;
	private websocket
	
	
	
	/**
	 * 
	 */
	@Override
	protected void initChannel(Channel ch) throws Exception {
		
	}

	
	
	
}
