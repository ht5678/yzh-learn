package org.ms.cloud.gateway.component.filter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ms.cloud.gateway.component.common.TokenInfo;
import org.ms.cloud.gateway.component.exception.GateWayException;
import org.ms.cloud.gateway.component.exception.SystemErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 
 * 鉴权过滤器 , 判断用户是否有权限访问api
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午9:52:32
 */
@Component
public class AuthenticationFilter implements GlobalFilter,Ordered,InitializingBean{
	
	//请求各个微服务  不需要认证的url
	private static Set<String> shouldSkipUrl = new LinkedHashSet<>();

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	
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
		shouldSkipUrl.add("/user/getCurrentUser");
	}

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//获取当前请求的路径
		String reqPath = exchange.getRequest().getURI().getPath();
		
		TokenInfo tokenInfo = exchange.getAttribute("tokenInfo");
		
		//无需拦截,直接放行
		if(shouldSkip(reqPath)) {
			LOGGER.info("无需认证的路径1");
			return chain.filter(exchange);
		}
		
		if(!tokenInfo.isActive()) {
			LOGGER.warn("token过期");
			throw new GateWayException(SystemErrorType.TOKEN_TIMEOUT);
		}
		
		boolean hasPermission = false;
		//登录用户的权限集合判断
		List<String> authorities = Arrays.asList(tokenInfo.getAuthorities());
		for(String url : authorities) {
			if(reqPath.contains(url)) {
				hasPermission = true;
				break;
			}
		}
		
		if(!hasPermission) {
			LOGGER.warn("权限不足");
			throw new GateWayException(SystemErrorType.FORBIDDEN);
		}
		
		return chain.filter(exchange);
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
