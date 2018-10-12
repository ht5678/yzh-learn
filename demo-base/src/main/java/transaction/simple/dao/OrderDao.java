package transaction.simple.dao;

import transaction.simple.model.Order;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午3:58:36
 *
 */
public interface OrderDao {
	
	
	/**
	 * 
	 * @return
	 */
	public int updateByPrimaryKeySelective(Order order);
	
	
	/**
	 * 
	 * @return
	 */
	public int updateStatusByPrimaryKeyAndStatus(Integer orderId , int nextStatus  , int preStatus);

}
