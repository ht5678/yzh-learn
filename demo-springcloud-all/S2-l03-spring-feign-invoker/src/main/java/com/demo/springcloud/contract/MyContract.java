package com.demo.springcloud.contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.MethodMetadata;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年8月22日 下午10:17:59
 *
 */
public class MyContract extends SpringMvcContract{

	
	
	
	@Override
	protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
		//其他注解的处理
		super.processAnnotationOnMethod(data, methodAnnotation, method);
		System.out.println("自定义注解处理");
		//自定义注解的处理
		if(MyUrl.class.isInstance(methodAnnotation)){
			MyUrl myUrl = method.getAnnotation(MyUrl.class);
			String url = myUrl.url();
			String httpMethod = myUrl.method();
			
			data.template().method(httpMethod);
			data.template().append(url);
		}
	}

	
}
