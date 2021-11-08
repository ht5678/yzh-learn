package org.sso.auth.jwt.server.config.indb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.sso.auth.jwt.server.config.role.domain.DemoUser;

/**
 * token增强器,根据自己的业务, 往token存储业务字段
 * @author yuezh2@lenovo.com
 *	@date 2021年11月8日下午2:58:43
 */
public class DemoTokenEnhancer implements TokenEnhancer{

	
	
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DemoUser demoUser = (DemoUser)authentication.getPrincipal();
		
		final Map<String, Object> additionalInfo = new HashMap<>();
		final Map<String, Object> retMap = new HashMap<>();
		
		additionalInfo.put("email", demoUser.getEmail());
		additionalInfo.put("phone", demoUser.getPhone());
		additionalInfo.put("userId", demoUser.getUserId());
		
		retMap.put("additionalInfo", additionalInfo);
		
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
		
		return accessToken;
	}

	
	
	
}
