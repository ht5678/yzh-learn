package org.ms.cloud.gateway.component.filter;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.ms.cloud.gateway.component.common.MDA;
import org.ms.cloud.gateway.component.common.TokenInfo;
import org.ms.cloud.gateway.component.exception.GateWayException;
import org.ms.cloud.gateway.component.exception.SystemErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午6:21:00
 */
@Component
public class AuthorizationFilter implements GlobalFilter,Ordered,InitializingBean{
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 请求各个服务,不需要用户验证的url
	 */
	private static Set<String> shouldSkipUrl = new LinkedHashSet<>();
	
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	
	/**
	 * 实际上,这边需要通过去数据库读取  不需要认证的url ,  不需要认证url是各个微服务
	 * 开发模块的人提供出来的 , 我在这里没有去查询数据库了, 直接模拟写死 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//模拟商品详情接口不需要认证
		shouldSkipUrl.add("/product/selectProductInfoById");
		//去认证的请求,本来就不需要拦截
		shouldSkipUrl.add("/oauth/token");
		shouldSkipUrl.add("/oauth/check_token");
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String reqPath = exchange.getRequest().getURI().getPath();
		LOGGER.info("网关认证开始URL : {}" , reqPath);
		
		//1.不需要认证的url
		if(shouldSkip(reqPath)) {
			LOGGER.info("无需认证的路径0");
			return chain.filter(exchange);
		}
		
		//获取请求头
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		
		//请求头为空
		if(StringUtils.isEmpty(authHeader)) {
			LOGGER.warn("需要认证的url , 请求头为空");
			throw new GateWayException(SystemErrorType.UNAUTHORIZED_HEADER_IS_EMPTY);
		}
		
		TokenInfo tokenInfo = null;
		
		try {
			//获取token信息
			tokenInfo = getTokenInfo(authHeader);
		}catch(Exception e) {
			LOGGER.warn("校验令牌异常:{}" ,authHeader);
			throw new GateWayException(SystemErrorType.INVALID_TOKEN);
		}
		
		//向headers中放文件,记得build
		ServerHttpRequest request = exchange.getRequest().mutate().header("userName", tokenInfo.getUser_name()).build();
		//将现在的request对象变成exchange对象
		ServerWebExchange serverWebExchange = exchange.mutate().request(request).build();
		
		serverWebExchange.getAttributes().put("tokenInfo", tokenInfo);
		
		return chain.filter(serverWebExchange);
	}

	
	
	private TokenInfo getTokenInfo(String authHeader) {
		String token = StringUtils.substringAfter(authHeader, "bearer ");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(MDA.clientId, MDA.clientSecret);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("token", token);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(params, headers);
		
		ResponseEntity<TokenInfo> response = restTemplate.exchange(MDA.checkTokenUrl, HttpMethod.POST , entity , TokenInfo.class);
		
		LOGGER.info("token info : "+response.getBody().toString());
				
		return response.getBody();
	}
	
	
	
	/**
	 * 不需要授权的url
	 * @param reqPath
	 * @return
	 */
	private boolean shouldSkip(String reqPath) {
		for(String skipPath: shouldSkipUrl) {
			if(reqPath.contains(skipPath)) {
				return true;
			}
		}
		return false;
	}
	
}
