package org.demo.netty.im.simple.v4.im.initializer;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.coder.PacketEncoder;
import org.demo.netty.im.fake.util.JsonSupport;
import org.demo.netty.im.fake.util.PacketDecoder;
import org.demo.netty.im.simple.v4.im.bs.handler.AuthorizationHandler;
import org.demo.netty.im.simple.v4.im.handler.PollingTransport;
import org.demo.netty.im.simple.v4.im.handler.WebsocketTransport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslHandler;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月13日 下午5:50:32
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
	private SSLContext sslContext;
	
	private AuthorizationHandler authorizeHandler;
//	
	private PollingTransport xhrPollingTransport;
	private WebsocketTransport websocketTransport;
//	private WrongUrlHandler wrongUrlHandler;
//	private EncoderHandler encoderHandler;
	
	
	
	
	/**
	 * 
	 */
	public BsChannelInitializer(Configuration config) {
		this.config = config;
		init();
	}
	
	
	
	/**
	 * 
	 */
	private void init() {
		JsonSupport jsonSupport = config.getJsonSupport();
		PacketEncoder encoder = new PacketEncoder(true, jsonSupport);
		PacketDecoder decoder = new PacketDecoder(jsonSupport);
		boolean isSsl = config.getKeyStore() != null;
		
		if(isSsl) {
			try {
				sslContext = createSslContext(config);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		
		//
		authorizeHandler = new AuthorizationHandler(config, decoder);
		xhrPollingTransport = new PollingTransport(config);
		websocketTransport = new WebsocketTransport(config, isSsl, encoder, decoder);
//		encoderHandler = new EncoderHandler(config , encoder);
//		wrongUrlHandler = new WrongUrlHandler();
		
	}
	
	
	/**
	 * 
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
//		OCIMServer.getInst().getScheduler().update(ctx);
	}
	
	
	
	/**
	 * 
	 */
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		addSslHandler(pipeline);
		addSocketIoHandler(pipeline);
	}
	
	
	
	
	
	
	/**
	 * 
	 */
	private void addSocketIoHandler(ChannelPipeline pipeline) {
		pipeline.addLast(HTTP_REQUEST_DECODER , new HttpRequestDecoder());
		pipeline.addLast(HTTP_AGGREGATOR , new HttpObjectAggregator(config.getMaxFramePayloadLength()) {
			@Override
			protected Object newContinueResponse(HttpMessage start, int maxContentLength, ChannelPipeline pipeline) {
				return null;
			}
		});
		//
		pipeline.addLast(HTTP_ENCODER , new HttpResponseEncoder());
		if(config.isHttpCompression()) {
			pipeline.addLast(HTTP_COMPRESSION , new HttpContentCompressor());
		}
		//
		pipeline.addLast(AUTHORIZE_HANDLER , authorizeHandler);
		pipeline.addLast(XHR_POLLING_TRANSPORT , xhrPollingTransport);
		if(config.isWebsocketCompression()) {
			pipeline.addLast(WEB_SOCKET_TRANSPORT_COMPRESSION , new WebSocketServerCompressionHandler());
		}
		pipeline.addLast(WEB_SOCKET_TRANSPORT , websocketTransport);
//		pipeline.addLast(SOCKET_TO_ENCODER , encoderHandler);
//		pipeline.addLast(WRONG_URL_HANDLER , wrongUrlHandler);
	}
	
	
	
	/**
	 * 
	 */
	private void addSslHandler(ChannelPipeline pipeline) {
		if(sslContext != null) {
			SSLEngine engine = sslContext.createSSLEngine();
			engine.setUseClientMode(false);
			pipeline.addLast(SSL_HANDLER , new SslHandler(engine));
		}
	}

	
	
	/**
	 * 
	 * @return
	 */
	private SSLContext createSslContext(Configuration config) throws Exception {
		TrustManager[] managers = null;
		if(config.getTrustStore() != null) {
			KeyStore ts = KeyStore.getInstance(config.getTrustStoreFormat());
			ts.load(config.getTrustStore() , config.getTrustStorePassword().toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ts);
			managers = tmf.getTrustManagers();
		}
		
		KeyStore ks = KeyStore.getInstance(config.getKeyStoreFormat());
		ks.load(config.getKeyStore() , config.getKeyStorePassword().toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(config.getKeyManagerFactoryAlgorithm());
		kmf.init(ks , config.getKeyStorePassword().toCharArray());
		
		SSLContext serverContext = SSLContext.getInstance(config.getSslProtocol());
		serverContext.init(kmf.getKeyManagers(), managers, null);
		return serverContext;
	}
	
	
	
}
