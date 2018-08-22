package com.demo.springcloud.feign;

import feign.Feign;
import feign.gson.GsonEncoder;

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
 * @author yuezh2   2018年8月22日 下午2:20:53
 *
 */
public class JsonTest {

	
	public static void main(String[] args) {
		HelloService service = Feign.builder().encoder(new GsonEncoder()).target(HelloService.class ,"http://localhost:8080");
		Police p = new Police();
		p.setMessage("gg");
		p.setId(1);
		p.setName("auguest");
		
		String result = service.createPerson(p);
		System.out.println(result);
	}
	
}
