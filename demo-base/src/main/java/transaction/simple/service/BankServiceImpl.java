package transaction.simple.service;

import org.springframework.stereotype.Component;

import transaction.simple.model.Order;
import transaction.simple.model.RespEntity;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午4:33:23
 *
 */
@Component
public class BankServiceImpl implements BankService {

	
	/**
	 * 
	 * @return
	 */
	public RespEntity<Object> outMoney(Order order){
		System.out.println("执行了方法BankServiceImpl.outMoney("+order+")");
		return null;
	}
	
}
