package com.demo.springcloud.feign.simple;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

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
 * @author yuezh2   2018年8月21日 下午10:10:32
 *
 */
public interface HelloService {

	/**
	 * 
	 * @return
	 */
	@RequestLine("GET /hello")
	public String hello();
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestLine("GET /call/{id}")
	public Police getPolice(@Param("id")Integer id);
	
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	@RequestLine("POST /person/create")
	@Headers("Content-Type: application/json")
	public String createPerson(Police p);
	
	
	@RequestLine("POST /person/createXML")
	@Headers("Content-Type: application/xml")
	public Result createXMLPerson(Police p);
	
}
