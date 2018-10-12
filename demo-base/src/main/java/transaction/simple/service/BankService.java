package transaction.simple.service;

import transaction.simple.model.Order;
import transaction.simple.model.RespEntity;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午4:33:10
 *
 */
public interface BankService {

	/**
	 * 
	 * @return
	 */
	public RespEntity<Object> outMoney(Order order);
	
}
