package org.demo.netty.im.fake.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:47:38
 */
public final class HttpResponses {
	
	public static void sendBadRequestError(Channel channel) {
		HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
		channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void sendUnauthorizedError(Channel channel) {
		HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
		channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void sendSuccess(Channel channel) {
		HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}
}