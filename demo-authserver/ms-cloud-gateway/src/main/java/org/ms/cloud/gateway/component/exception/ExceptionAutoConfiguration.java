package org.ms.cloud.gateway.component.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * 
 * 这里通过观察网关,  ErrorWebFluxAutoConfiguration 的配置
 * 我们只需要把 该配置类copy下来 , 然后定义errorWebExceptionHandler类,
 * 
 * 我们需要写一个自定义的异常处理器
 *  
 * @author yuezh2@lenovo.com
 *	@date 2021年10月20日下午4:52:12
 */
@Configuration
@ConditionalOnWebApplication(type= ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(WebFluxConfigurer.class)
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ExceptionAutoConfiguration {

	private ServerProperties serverProperties;
	
	private ApplicationContext applicationContext;
	
	private ResourceProperties resourceProperties;
	
	private List<ViewResolver> viewResolvers;
	
	private ServerCodecConfigurer serverCodeConfigurer;
	
	
	
	
	
	/**
	 * 
	 * @param serverProperties
	 * @param resourceProperties
	 * @param viewResolversProvider
	 * @param serverCodecConfigurer
	 * @param applicationContext
	 */
	public ExceptionAutoConfiguration(ServerProperties serverProperties , 
														ResourceProperties resourceProperties,
														ObjectProvider<List<ViewResolver>> viewResolversProvider,
														ServerCodecConfigurer serverCodecConfigurer,
														ApplicationContext applicationContext) {
		this.serverProperties = serverProperties;
		this.resourceProperties = resourceProperties;
		this.applicationContext = applicationContext;
		this.viewResolvers = viewResolversProvider.getIfAvailable(() -> Collections.emptyList());
		this.serverCodeConfigurer = serverCodecConfigurer;
	}
	
	
	
	/**
	 * 
	 * @param errorAttributes
	 * @return
	 */
	@Bean
	public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
		DefaultErrorWebExceptionHandler exceptionHandler = new CustomerErrorWebExceptionHandler(
					errorAttributes, this.resourceProperties, this.serverProperties.getError(), applicationContext);
		exceptionHandler.setViewResolvers(this.viewResolvers);
		exceptionHandler.setMessageWriters(this.serverCodeConfigurer.getWriters());
		exceptionHandler.setMessageReaders(this.serverCodeConfigurer.getReaders());
		return exceptionHandler;
	}
	
}
