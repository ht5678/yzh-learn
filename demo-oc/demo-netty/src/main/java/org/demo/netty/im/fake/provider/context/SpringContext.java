package org.demo.netty.im.fake.provider.context;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:42:24
 */
public class SpringContext {

	private static ApplicationContext context = null;
	
	
	/**
	 * 
	 */
	@Resource
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContext.context = applicationContext;
	}
	
	
	public static <T> T getBean(Class<T> type) {
		if (null == context) {
			return null;
		}
		return context.getBean(type);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (null == context) {
			return null;
		}
		return (T)context.getBean(name);
	}
	
}
