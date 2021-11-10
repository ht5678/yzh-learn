package org.sso.api.gateway.jwt.component.filter;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.ms.cloud.common.component.exception.GateWayException;
import org.ms.cloud.common.component.exception.SystemErrorType;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.sso.api.gateway.jwt.component.common.MDA;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器,根据url判断用户请求是要经过认证   才能访问
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午3:29:57
 */
@Component
public class AuthorizationFilter implements GlobalFilter,Ordered,InitializingBean{
	
	@Autowired
	private RestTemplate restTemplate;
	
	private PublicKey publicKey;
	
	//请求各个微服务,不需要用户认证的url
	private static Set<String> shouldSkipUrl = new LinkedHashSet<>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	/*
	 * 
	 * 网关改造:sso-api-gateway-jwt 因为网关启动的时候 需要去认证中心获取jwt解析的公钥。
		1）网关启动的时候 需要通过远程调用 去获取jwt的公钥(****在这里Ribbon是不起作用的)
		在这里我们实现了InitializingBean 去获取jwt的公钥.
		2)自己需要写一个负载均衡的组件 来实现负载均衡的功能.
	 * 
	 * 
	 * */
	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String reqPath = exchange.getRequest().getURI().getPath();
		LOGGER.info("网关认证开始URL->:{}",reqPath);
		
		//1.不需要认证的url
		if(shouldSkip(reqPath)) {
			LOGGER.info("无需认证的URL");
			return chain.filter(exchange);
		}
		
		//获取请求头
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		
		//请求头为空
		if(StringUtils.isEmpty(authHeader)) {
			LOGGER.warn("需要认证的url , 请求头为空");
			throw new GateWayException(SystemErrorType.UNAUTHORIZED_HEADER_IS_EMPTY);
		}
		
		
		//交易我们的jwt,若jwt不对或者超时都会抛异常
		Claims claims = validateJwtToken(authHeader);
		
		//向headers中放文件 , 记得build
		ServerHttpRequest request = exchange.getRequest().mutate().header("username", claims.get("user_name").toString()).build();
		//将现在的request对象变成change对象
		ServerWebExchange serverWebExchange = exchange.mutate().request(request).build();
		
		//从jwt中解析出权限集合进行判断
		hasPermission(claims, reqPath);
		
		return chain.filter(serverWebExchange);
	}
	
	
	
	
	/**
	 * 
	 * @param claims
	 * @param currentUrl
	 * @return
	 */
	private boolean hasPermission(Claims claims , String currentUrl) {
		boolean hasPermission = false;
		//登录用户的权限集合判断
		List<String> permissionList = claims.get("authorities",List.class);
		for(String url : permissionList) {
			if(currentUrl.contains(url)) {
				hasPermission = true;
				break;
			}
		}
		
		if(!hasPermission) {
			LOGGER.warn("权限不足");
			throw new GateWayException(SystemErrorType.FORBIDDEN);
		}
		return hasPermission;
	}
	
	
	
	
	
	/**
	 * 
	 * @param authHeader
	 * @return
	 */
	private Claims validateJwtToken(String authHeader) {
		String token = null;
		try {
			token = StringUtils.substringAfter(authHeader, "bearer ");
			
			Jwt<JwsHeader, Claims> parseClaimsJwt = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			
			Claims claims = parseClaimsJwt.getBody();
			LOGGER.info("claims : {}" , claims);
			return claims;
		}catch(Exception e) {
			LOGGER.error("校验token异常 : {}, 异常信息: {} ",token , e.getMessage());
			throw new GateWayException(SystemErrorType.INVALID_TOKEN);
		}
	}
	
	
	
	
	/**
	 * 不需要授权的url
	 * @param reqPath
	 * @return
	 */
	private boolean shouldSkip(String reqPath) {
		for(String skipPath : shouldSkipUrl) {
			if(reqPath.contains(skipPath)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	/**
	 * 
	 */
	@Override
	public int getOrder() {
		return 0;
	}
	
	
	
	
	
	
	
	/**
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//实际上 , 这边需要从db读取,不需要认证的url 都是各个微服务开发模块的人员提出来的, 
		//我在这里诶呦查询db了 , 直接硬编码
        shouldSkipUrl.add("/oauth/token");
        shouldSkipUrl.add("/oauth/check_token");
        shouldSkipUrl.add("/user/getCurrentUser");
		
        //初始化公钥
        this.publicKey = getPublicKeyByTokenKey();
	}

	
	private PublicKey getPublicKeyByTokenKey() {
		try {
			String tokenKey = getTokenKey();
			
			String dealTokenKey = tokenKey.replaceAll("\\-*BEGIN PUBLIC KEY\\-*", "").replaceAll("\\-*END PUBLIC KEY\\-*", "").trim();
			
			Security.addProvider(new BouncyCastlePQCProvider());
			
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(dealTokenKey));
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
			
			LOGGER.info("生成公钥 : {}",publicKey);
			
			return publicKey;
		}catch(Exception e) {
			LOGGER.error("生成公钥异常 : {}",e.getMessage());
			throw new GateWayException(SystemErrorType.INVALID_TOKEN);
		}
	}
	
	
	
	
	/**
	 * 去认证服务器获取tokenKey
	 * @return
	 */
	private String getTokenKey() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(MDA.clientId, MDA.clientSecret);
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null , headers);
		
		try {
			ResponseEntity<Map> response = restTemplate.exchange(MDA.getTokenKey, HttpMethod.GET,entity , Map.class);
			
			String tokenKey = response.getBody().get("value").toString();
			
			LOGGER.info("去认证服务器获取TokenKey : {}" , tokenKey);
			
			return tokenKey;
		}catch(Exception e) {
			LOGGER.error("远程调用认证服务器获取tokenKey失败 : {}" , e.getMessage());
			throw new GateWayException(SystemErrorType.INVALID_TOKEN);
		}
		
	}
	
	

	
	
}
