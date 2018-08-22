package com.demo.springcloud.feign.simple;

import feign.Feign;
import feign.gson.GsonDecoder;

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
 * 自定义的client
 * 
 * @author yuezh2   2018年8月22日 下午4:14:34
 *
 */
public class MyClientTest {
	
	
	public static void main(String[] args) {
		HelloService service = Feign.builder().client(new MyClient())
				.decoder(new GsonDecoder()).target(HelloService.class,"http://localhost:8080");
		
		String result = service.hello();
		System.out.println(result);
		
		//
		Police p = service.getPolice(1);
		System.out.println(p.getName());
		System.out.println(p.getMessage());
	}

}
