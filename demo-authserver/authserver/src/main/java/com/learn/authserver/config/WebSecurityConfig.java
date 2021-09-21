package com.learn.authserver.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * 授权中心安全配置
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年9月17日下午8:53:15
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Resource(name="userDetailsService")
	private DemoUserDetailService demoUserDetailService;
	
	
	/**
	 * 
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(demoUserDetailService);
		auth.userDetailsService(demoUserDetailService).passwordEncoder(passwordEncoder());
	}

	/**
	 * 密码加密器组件
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
//		return NoOpPasswordEncoder.getInstance();
	}
	
	
	//in memory 
//	@Override
//	protected UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build());
//		manager.createUser(User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build());
//		return manager;
//	}

	/**
	 * 上面的configure 真正的构建好了我们的authenticationManagerBuilder ,  
	 * 我们在这里需要通过建造者 , 构建我们的authenticationManager对象
	 * 
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManager();
	}
	
}
