package org.springcloud.log.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月28日 下午2:54:43
 *
 */
@RestController
public class SimpleLogController {

	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	
	
	@RequestMapping("/log/simple")
	public void test(){
		logger.info("this is a test");
	}
	
}
