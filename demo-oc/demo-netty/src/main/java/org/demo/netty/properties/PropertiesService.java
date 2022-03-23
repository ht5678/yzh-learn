package org.demo.netty.properties;

import org.demo.netty.domain.Properties;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午4:23:43
 */
public interface PropertiesService {

	
	Properties obtainProperties(String tenantCode);
	
}
