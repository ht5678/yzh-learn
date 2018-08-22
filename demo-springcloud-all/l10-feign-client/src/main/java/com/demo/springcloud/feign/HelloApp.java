package com.demo.springcloud.feign;

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
 * 
 * @author yuezh2   2018年8月21日 下午10:11:19
 *
 */
public class HelloApp {
	
	
	/**
	 * 测试的时候需要启动 cloud-police , 用8080端口
	 * @param args
	 */
	public static void main(String[] args) {
		HelloService service = Feign.builder()
				.decoder(new GsonDecoder()).target(HelloService.class,"http://localhost:8080");
		
		String result = service.hello();
		System.out.println(result);
		
		//
		Police p = service.getPolice(1);
		System.out.println(p.getName());
		System.out.println(p.getMessage());
		
	}

}
