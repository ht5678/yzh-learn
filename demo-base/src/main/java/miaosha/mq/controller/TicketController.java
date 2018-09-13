package miaosha.mq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import miaosha.mq.model.TicketInfo;
import miaosha.mq.service.TicketService;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:14:23
 *
 */
@Controller
public class TicketController {

	
	@Autowired
	private TicketService ticketService;
	
	
	
	/**
	 * 
	 * @param idcard
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mq/buyTicket")
	public String buyTicket(@RequestParam(required=false)String idcard){
		TicketInfo ticket = null;
		try{
			//模拟购票
			System.out.println("开始购票业务......");
			System.out.println("购票成功......");
			ticket = ticketService.getTicket("12345");
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSON.toJSONString(ticket);
	}
	
	
}
