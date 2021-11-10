package org.sso.api.gateway.jwt.config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sso.api.gateway.jwt.component.common.DemoRestTemplate;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午3:18:22
 */
@Configuration
public class RibbonConfig {
	

	
	/**
	 * 原生的RestTemplate + @LB 不行 , 因为在InitializingBean方法执行前我们的RestTemplate还没被增强
	 * @return
	 */
	@Bean
	public DemoRestTemplate restTemplate(DiscoveryClient discoveryClient) {
		return new DemoRestTemplate(discoveryClient);
	}

}
