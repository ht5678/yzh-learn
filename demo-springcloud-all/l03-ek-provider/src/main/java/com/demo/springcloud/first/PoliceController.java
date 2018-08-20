package com.demo.springcloud.first;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
 * @author yuezh2   2018年8月17日 下午3:51:13
 *
 */
@RestController
public class PoliceController {

	
	
	@RequestMapping(value="call/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Police call(@PathVariable Integer id){
		Police p = new Police();
		p.setId(1);
		p.setName("zahngsa");
		return p;
	}
	
	
	
	public static boolean canVisitDb = true;
	
	
	/**
	 * 
	 * @param can
	 */
	@RequestMapping(value="db/{can}",method=RequestMethod.GET)
	public void setDb(@PathVariable boolean can){
		canVisitDb=can;
	}
	
}
