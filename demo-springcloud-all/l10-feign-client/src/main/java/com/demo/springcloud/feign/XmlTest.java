package com.demo.springcloud.feign;

import feign.Feign;
import feign.gson.GsonEncoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;

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
 * @author yuezh2   2018年8月22日 下午4:03:42
 *
 */
public class XmlTest {
	
	
	public static void main(String[] args) {
		JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().build();
		
		HelloService service = Feign.builder().encoder(new JAXBEncoder(jaxbFactory))
				.decoder(new JAXBDecoder(jaxbFactory))
				.target(HelloService.class ,"http://localhost:8080");
		
		Police p = new Police();
		p.setId(1);
		p.setName("zhangsan");
		p.setMessage("xx");
		
		Result result = service.createXMLPerson(p);
		System.out.println(result.getMessage());
		
	}

}
