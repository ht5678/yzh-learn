package com.learn.common.http;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 *
 */
public class DefaultHttpClientAceessLog implements ApiHttpClient.AccessLog{
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHttpClientAceessLog.class);


    @Override
    public void accessLog(long time, String method, int status, int len, String url, String post, String ret) {
        accessLog(time, method, status, len, url, null, post, ret);
    }

	public void accessLog(long time, String method, int status, int len,
						  String url, Map<String, String> headerMap, String post, String ret) {
		if (post != null && post.length() > 200) {
			post = post.substring(0, 200);
			post.replaceAll("\n", "");
			post = post + "...";
		}
		if (ret != null) {
			ret = ret.trim();
			ret = ret.replaceAll("\n", "");
		}
		if (!StringUtils.isBlank(post) && url.startsWith("http://ilogin.sina.com.cn")) {
			post = replacePwd(post);
		}
		String header = Objects.isNull(headerMap) ? "" : headerMap.toString();
		LOGGER.info(format("%s %s %s %s %s %s %s %s", time, method, status, len, url, header,
				isEmpty(post) ? "-" : post, isEmpty(ret) ? "-" : ret));


		//过滤url
		if(url.startsWith("https://i.app.lefile.cn/uc_server/")){
			return;
		}

		//kafka
		//添加方法参数
		String mallMethod = "";
		if("http://openapi.lenovo.com.cn/router/json.jhtm".equals(url) && StringUtils.isNotEmpty(post)){
			String[] paramArr = post.split("&");
			for(String param : paramArr){
				if(param.contains("method")){
					mallMethod = param;
					break;
				}
			}
		}

		if(url.contains("?")){
			url=url.substring(0,url.indexOf("?")+1)+mallMethod;
		}else{
			url = url +"?"+mallMethod;
		}

		String appId = "901";
		//ApiLogger.accesslog(url+","+method+","+time+","+appId+","+0+","+"0.0.0.0"+","+0+",");
	}

	static String replacePwd(String text){
		text = text.replaceFirst("pw=[^&]*", "pw=***");
		return text;
	}

}
