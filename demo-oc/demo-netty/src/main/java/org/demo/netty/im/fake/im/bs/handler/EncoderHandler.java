package org.demo.netty.im.fake.im.bs.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.bs.message.Message;
import org.demo.netty.im.fake.im.bs.message.OutPacketMessage;
import org.demo.netty.im.fake.im.bs.message.PollErrorMessage;
import org.demo.netty.im.fake.im.bs.message.PollOkMessage;
import org.demo.netty.im.fake.im.bs.message.PongPacketMessage;
import org.demo.netty.im.fake.im.coder.PacketEncoder;
import org.demo.netty.im.fake.session.Session;
import org.demo.netty.im.fake.session.TransportStore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:52:01
 */
public class EncoderHandler extends ChannelOutboundHandlerAdapter{
	
	public static final AttributeKey<String> ORIGIN = AttributeKey.valueOf("origin");
	public static final AttributeKey<String> USER_AGENT = AttributeKey.valueOf("userAgent");
	
	private static final byte[] OK = "{\"ret\":\"ok\"}".getBytes(StandardCharsets.UTF_8);
	private static final byte[] ERROR = "{\"ret\":\"error\"}".getBytes(StandardCharsets.UTF_8);
	
	private final Configuration config;
	private final PacketEncoder encoder;
	
	private String version;
	
	public EncoderHandler(Configuration config, PacketEncoder encoder) {
		this.config = config;
		this.encoder = encoder;
	}
	
	/**
	 * @param ctx
	 * @param msg
	 * @param promise
	 * @throws Exception
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (!(msg instanceof Message)) {
			super.write(ctx, msg, promise);
			return;
		}
		
		if (msg instanceof OutPacketMessage) {
			OutPacketMessage outMessage = (OutPacketMessage)msg;
			if (outMessage.getTransport() == Transport.WEBSOCKET) {
				handleWebsocket(outMessage, ctx, promise);
			} else if (outMessage.getTransport() == Transport.POLLING) {
				handleHttp(outMessage, ctx, promise);
			}
		} else if (msg instanceof PongPacketMessage) {
			write((PongPacketMessage)msg, ctx, promise);
		} else  if (msg instanceof PollOkMessage) {
			write((PollOkMessage)msg, ctx, promise, HttpResponseStatus.OK);
		} else if (msg instanceof PollErrorMessage) {
			write((PollErrorMessage)msg, ctx, promise);
		}
	}
	
	private void write(PongPacketMessage msg, ChannelHandlerContext ctx, ChannelPromise promise) throws IOException {
		ByteBuf out = encoder.allocateBuffer(ctx.alloc());
		encoder.encodePacket(msg.getPacket(), out, ctx.alloc(), 50);
		
		HttpResponse response = bulidHttpResponse(HttpResponseStatus.OK);
		sendPacket(ctx, out, "application/json", promise, response);
	}

	private void handleWebsocket(OutPacketMessage outPacketMessage, ChannelHandlerContext ctx, ChannelPromise promise) throws IOException {
		CustomChannelFutureListener cfl = new CustomChannelFutureListener();
		Session session = outPacketMessage.getSession();
		TransportStore store = session.transportStore();
		
		if (store.getTransport() != Transport.WEBSOCKET) {
			return;
		}
		Queue<Packet> packets = store.getPacketsQueue();
		
		while (true) {
			Packet packet = packets.poll();
			if (packet == null) {
				cfl.setPromise(promise);
				break;
			}
			
			ByteBuf out = encoder.allocateBuffer(ctx.alloc());
			encoder.encodePacket(packet, out, ctx.alloc());
			
			WebSocketFrame res = new TextWebSocketFrame(out);
			if (out.isReadable()) {
				cfl.add(ctx.channel().writeAndFlush(res));
            } else {
                out.release();
            }
		}
	}
	
	private void handleHttp(OutPacketMessage wsMessage, ChannelHandlerContext ctx, ChannelPromise promise) throws IOException {
		Channel channel = ctx.channel();
		Session session = wsMessage.getSession();

		TransportStore store = session.transportStore();
		if (store.getTransport() != Transport.POLLING) {
			return;
		}
		
		Queue<Packet> packetsQueue = store.getPacketsQueue();
		if (packetsQueue.isEmpty() || !channel.isActive()) {
			promise.trySuccess();
			return;
		}
		
		ByteBuf out = encoder.allocateBuffer(ctx.alloc());
		encoder.encodePackets(packetsQueue, out, ctx.alloc(), 50);
		
		HttpResponse response = bulidHttpResponse(HttpResponseStatus.OK);
		sendPacket(ctx, out, "application/json", promise, response);
	}
	
	private void write(PollErrorMessage pollMsg, ChannelHandlerContext ctx, ChannelPromise promise) {
		ByteBuf out = encoder.allocateBuffer(ctx.alloc());
		out.writeBytes(ERROR);
		HttpResponse response = bulidHttpResponse(HttpResponseStatus.OK);
		sendPacket(ctx, out, "application/json", promise, response);
	}
	
	private void write(PollOkMessage okMessage, ChannelHandlerContext ctx, ChannelPromise promise, HttpResponseStatus status) {
		ByteBuf out = encoder.allocateBuffer(ctx.alloc());
		out.writeBytes(OK);
		HttpResponse response = bulidHttpResponse(HttpResponseStatus.OK);
		sendPacket(ctx, out, "application/json", promise, response);
	}
	
	private void sendPacket(ChannelHandlerContext ctx, ByteBuf out, String type,
			ChannelPromise promise, HttpResponse response) {
		response.headers().add(HttpHeaderNames.CONTENT_TYPE, type).add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		
		String origin = ctx.channel().attr(ORIGIN).get();
		
		addOriginHeaders(origin, response);
		
		String userAgent = ctx.channel().attr(EncoderHandler.USER_AGENT).get();
		if (userAgent != null && (userAgent.contains(";MSIE") || userAgent.contains("Trident/"))) {
			response.headers().add("X-XSS-Protection", "0");
		}
		
		HttpUtil.setContentLength(response, out.readableBytes());
		
		sendPacket(ctx.channel(), out, response, promise);
	}

	private void sendPacket(Channel channel, ByteBuf out, HttpResponse resq, ChannelPromise promise) {
		
		channel.write(resq);
		
		if (out.isReadable()) {
			channel.write(new DefaultHttpContent(out));
		} else {
			out.release();
		}
		channel.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT, promise).addListener(ChannelFutureListener.CLOSE);
	}
	
	private void addOriginHeaders(String origin, HttpResponse res) {
        if (version != null) {
            res.headers().add(HttpHeaderNames.SERVER, version);
        }

        if (config.getOrigin() != null) {
            res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, config.getOrigin());
            res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE);
        } else {
            if (origin != null) {
                res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE);
            } else {
                res.headers().add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            }
        }
    }
	
	/**
	 * 
	 * 
	 * 由于本身使用http请求都是，请求-应答这种方式，一直觉得http是短连接的。每次通讯后，其连接断开。javascript

		其实否则，http1.1开始。客户端的请求头带上html
		
		Connection: keep-alive
		即是维持长连接。固然这个须要服务器的支持。
	 *		
	 *		
	 *		
	 * @param status
	 * @return
	 */
	private HttpResponse bulidHttpResponse(HttpResponseStatus status) {
		return new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
	}
	
	
	//将指定的监听器添加到此Future，future完成时，会通知此监听器，如果添加监听器时future已经完成，则立即通知此监听器
	private class CustomChannelFutureListener implements GenericFutureListener<Future<Void>> {

		private List<ChannelFuture> futureList = new ArrayList<>();
		private ChannelPromise promise = null;
		
		private void clearup() {
			promise = null;
			for (ChannelFuture cf : futureList) {
				cf.removeListener(this);
			}
		}
		
		private void validate() {
			boolean allSuccess = true;
			for (ChannelFuture cf : futureList) {
				if (cf.isDone()) {
					if (!cf.isSuccess()) {
						//cause() 如果i/o操作失败，返回其失败原因。如果成功完成或者还未完成，返回null
						promise.tryFailure(cf.cause());
						clearup();
						return;
					}
				} else {
					allSuccess = false;
				}
			}
			if (allSuccess) {
				promise.trySuccess();
				clearup();
			}
		}
		
		public void setPromise(ChannelPromise promise) {
			this.promise = promise;
			validate();
		}
		
		public void add(ChannelFuture cf) {
			futureList.add(cf);
			cf.addListener(this);
		}

		@Override
		public void operationComplete(Future<Void> future) throws Exception {
			if (null != promise) {
				validate();
			}
		}
	}
}
