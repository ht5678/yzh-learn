package org.sso.auth.jwt.server.config.controller;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sso.auth.jwt.server.config.role.domain.DemoUser;
import org.sso.auth.jwt.server.config.role.entity.SysUser;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;



/**
 * 
 * @author yue
 *
 */
@Controller
public class AuthLoginController {

	
    @Autowired
    private KeyPair keyPair;
    
    
	@Autowired
	private TokenStore tokenService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthLoginController.class);
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exit")
	public void exit(HttpServletRequest request , HttpServletResponse response){
		//token can be revoked here if needed
		new SecurityContextLogoutHandler().logout(request, null, null);
		try{
			LOGGER.info("current url : {}",request.getRequestURL());
			LOGGER.info("redirect page : {}" , request.getParameter("redirectUrl"));
			
			response.sendRedirect(request.getHeader("referer"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
    @GetMapping("/publickey/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
	
	
	/**
	 * @param accessToken
	 * @return
	 */
	@RequestMapping("/user/getCurrentUser")
	@ResponseBody
	public SysUser me(@RequestParam("access_token")String accessToken){
		OAuth2Authentication oAuth2Authentication = tokenService.readAuthentication(accessToken);
		
		DemoUser demoUser = (DemoUser)oAuth2Authentication.getUserAuthentication().getPrincipal();
		
		SysUser sysUser = new SysUser();
		sysUser.setId(demoUser.getUserId());
		sysUser.setEmail(demoUser.getEmail());
		sysUser.setUsername(demoUser.getUsername());
		sysUser.setPassword(demoUser.getPassword());
		
		return sysUser;
		
	}
	
	
	
}
