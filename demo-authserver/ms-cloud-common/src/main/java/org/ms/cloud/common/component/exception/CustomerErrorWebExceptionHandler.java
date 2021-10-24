package org.ms.cloud.common.component.exception;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午10:36:06
 */
public class CustomerErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler{

	
	@Autowired
	private GateWayExceptionHandlerAdvice gateWayExceptionHandlerAdvice;
	
	
	/**
	 * 
	 * @param errorAttributes
	 * @param resourceProperties
	 * @param errorProperties
	 * @param applicationContext
	 */
	public CustomerErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ErrorProperties errorProperties, ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, errorProperties, applicationContext);
	}

	
	

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(),this::renderErrorResponse);
	}


	@Override
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Map<String, Object> error = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus errorStatus = getHttpStatus(error);
		Throwable throwable = getError(request);
		return ServerResponse.status(errorStatus)
										.contentType(MediaType.APPLICATION_JSON_UTF8)
										.body(BodyInserters.fromObject(gateWayExceptionHandlerAdvice.handle(throwable)));
				
	}

	
	
	
}
