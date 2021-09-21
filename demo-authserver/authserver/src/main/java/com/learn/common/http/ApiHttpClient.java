package com.learn.common.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.Future;

public interface ApiHttpClient {
	
	
	/**
	 * post
	 * @param url
	 * @param body
	 * @param headers
	 * @param charset
	 * @param contentType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String post(String url, String body, Map<String, String> headers, String charset , String contentType) throws UnsupportedEncodingException;
	
	String get(String url);
	/**
	 * 异步方式get
	 * @param url
	 * @return
	 */
	String getAsync(String url);
	Future<String> getAsyncFuture(String url);
	/**
	 *  ResultConvert 转换字符串为对象方式，如json.
	 * @param <T>
	 * @param url
	 * @param c
	 * @return
	 */
	<T> T get(String url, ResultConvert<T> c);
	String get(String url,String charset);
	byte[] getByte(String url);
	byte[] getByte(String url, int size);
	String post(String url, Map<String, ?> nameValues);
	String postAsync(String url, Map<String, ?> nameValues);
	Future<String> postAsyncFuture(String url, Map<String, ?> nameValues);
	<T> T post(String url,Map<String, ?> nameValues, ResultConvert<T> c);
	String post(String url, Map<String, ?> nameValues,String charset);
	<T> T post(String url, Map<String, ?> nameValues,String charset, ResultConvert<T> c);
	/**
	 * Map<String, Object> nameValues value type support ByteArrayPart,FileItem,String
	 * @param url
	 * @param nameValues
	 * @param charset
	 * @return
	 */
	String postMulti(String url, Map<String, Object> nameValues,String charset);

	HttpClientResponse getPostResponse(String url, Map<String, ?> nameValues);

	HttpClientResponse getPostResponse(String url, Map<String, ?> nameValues, Map<String, String> headers, String charset);

	String postMulti(String url, Map<String, Object> nameValues);
	
	/**
	 * 直接将inputstream 作为 body，发送到服务端
	 * @param url
	 * @param in
	 * @return
	 */
	String postMulti(String url, InputStream in);
	
	/**
	 * 
	 * @param url
	 * @param buf
	 * @return
	 * @see #postMulti(String, InputStream)
	 */
	String postMulti(String url, byte[] buf);
	
	RequestBuilder buildGet(String url);
	RequestBuilder buildPost(String url);
	public interface ResultConvert<T>{
		T convert(String url,String post,String result);
	}
	
	/**
	 * 将结果转换成<T>
	 * @author yuezh2   2016年3月10日 下午4:53:51
	 *
	 */
	public abstract class AbstractResultConvert<T> implements ResultConvert<T> {

		@Override
		public abstract T convert(String url, String post, String result);

	}
	
	void setAccessLog(AccessLog accessLog);

	interface AccessLog {
		void accessLog(long time, String method, int status, int len, String url, String post, String ret);

		void accessLog(long time, String method, int status, int len, String url, Map<String, String> headerMap,
				String post, String ret);
	}
	public interface RequestBuilder {
		/**
		 * 设置请求的uri charset和content charset
		 * @param charset
		 * @return
		 */
		RequestBuilder withCharset(String charset);
		RequestBuilder withParam(Map<String,?> param);
		RequestBuilder withParam(boolean condition,String key,Object value);
		RequestBuilder withParam(String key,Object value);
		RequestBuilder withHeader(Map<String,String> header);
		RequestBuilder withHeader(boolean condition,String key,String value);
		RequestBuilder withHeader(String key,String value);
		<T> T execute(ResultConvert<T> convert);
		String execute();
		Future<String> executeAsync();
		String executeAsyncString();
		byte[] executeByte(); 
	}
}
