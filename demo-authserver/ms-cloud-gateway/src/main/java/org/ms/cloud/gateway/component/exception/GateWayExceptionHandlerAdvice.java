package org.ms.cloud.gateway.component.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午10:39:52
 */
@Component
public class GateWayExceptionHandlerAdvice {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(GateWayExceptionHandlerAdvice.class);
	
	
	
	@ExceptionHandler(value= {ResponseStatusException.class})
	public Result handle(ResponseStatusException ex) {
		LOGGER.error("response status exception : {}",ex.getMessage());
		return Result.fail(SystemErrorType.GATEWAY_ERROR); 
	}
	
	
	@ExceptionHandler(value= {NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Result handle(NotFoundException ex) {
		LOGGER.error("not found exception : {}",ex.getMessage());
		return Result.fail(SystemErrorType.GATEWAY_NOT_FOUND_SERVICE);
	}
	
	
}
