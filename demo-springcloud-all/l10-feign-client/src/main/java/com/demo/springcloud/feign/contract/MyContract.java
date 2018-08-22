package com.demo.springcloud.feign.contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import feign.Contract.BaseContract;
import feign.MethodMetadata;




public class MyContract extends BaseContract{

	@Override
	protected void processAnnotationOnClass(MethodMetadata arg0, Class<?> arg1) {
		
	}

	@Override
	protected void processAnnotationOnMethod(MethodMetadata data, Annotation annotation, Method method) {
		if(MyUrl.class.isInstance(annotation)){
			MyUrl myUrl = method.getAnnotation(MyUrl.class);
			String url = myUrl.url();
			String httpMethod = myUrl.method();
			
			data.template().method(httpMethod);
			data.template().append(url);//在locahost:8080后边加上url
		}
	}

	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata arg0, Annotation[] arg1, int arg2) {
		return false;
	}

	
	
}
