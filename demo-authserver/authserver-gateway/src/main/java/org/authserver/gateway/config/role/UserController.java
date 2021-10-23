package org.authserver.gateway.config.role;

import org.authserver.gateway.config.role.domain.DemoUser;
import org.authserver.gateway.config.role.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yue
 *
 */
@RestController
public class UserController {

	@Autowired
	private TokenStore tokenStore;
	
	
	@RequestMapping("/user/getCurrentUser")
	public SysUser getUser(@RequestParam("access_token")String accessToken){
		OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
		
		DemoUser demoUser = (DemoUser)oAuth2Authentication.getUserAuthentication().getPrincipal();
		
		SysUser sysUser = new SysUser();
		sysUser.setId(demoUser.getUserId());
		sysUser.setEmail(demoUser.getEmail());
		sysUser.setNickname(demoUser.getNickName());
		sysUser.setUsername(demoUser.getUsername());
		
		return sysUser;
		
	}
	
}
