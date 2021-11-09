package org.sso.auth.jwt.server.config.indb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.sso.auth.jwt.server.config.DemoUserDetailService;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午2:51:43
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DemoUserDetailService demoUserDetailsService;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	
	/**
	 * 用于构建用户认证组件 , 需要传递userDetailsService和密码加密器
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(demoUserDetailsService).passwordEncoder(passwordEncoder());
	}

	
	/**
	 * 设置前台静态资源不拦截
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**","/css/**","/images/**");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/login")  //自定义login  page
				.and()
				.logout()
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.authorizeRequests()
				.antMatchers("/login","/login.html","/user/getCurrentUser","/publickey/jwks.json").permitAll()
				.anyRequest()
				.authenticated()
				.and().csrf().disable().cors();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
    public static void main(String[] args) {
        new BCryptPasswordEncoder().encode("122222");
    }
    
}
