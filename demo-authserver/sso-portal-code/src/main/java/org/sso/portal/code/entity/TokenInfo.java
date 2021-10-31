package org.sso.portal.code.entity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 
 * @author yue
 *
 */
public class TokenInfo {

	
	private String access_token;
	
	private String token_type;
	
	private long expires_in;
	
	private String refresh_token;
	
	private String scope;
	
	private String loginUser;
	
	private LocalDateTime expireTime;
	
	private Map<String, String> additionInfo;
	
	
	
	/**
	 * 
	 * 初始化token到期时间
	 * 
	 * @return
	 */
	public TokenInfo initExpireTime(){
		this.expireTime  = LocalDateTime.now().plusSeconds(expires_in);
		return this;
	}
	
	
	/**
	 * 判断我们的access token是否失效
	 * @return
	 */
	public boolean isExpire(){
		//表示没有过期
		return LocalDateTime.now().isAfter(expireTime);
	}
	
	
	
	
	
	

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public Map<String, String> getAdditionInfo() {
		return additionInfo;
	}

	public void setAdditionInfo(Map<String, String> additionInfo) {
		this.additionInfo = additionInfo;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		Long expireIn = 10L;
		LocalDateTime expireTime = LocalDateTime.now().plusSeconds(expireIn);
		
		Thread.sleep(15000L);
		System.out.println(LocalDateTime.now().isAfter(expireTime));
	}
	
	
	
}
