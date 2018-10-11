package transaction.simple.service;

import transaction.simple.model.Order;

/**
 * 银行接口
 * @author sdwhy
 *
 */
public interface OrderService {

	
	/**
	 * 出款接口
	 * @param orderInfo
	 * @return
	 */
	public void pay(Order orderInfo);
	
}
