package transaction.simple.dao;

import org.springframework.stereotype.Component;

import transaction.simple.model.Order;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午3:58:50
 *
 */
@Component
public class OrderDaoImpl implements OrderDao {

	
	
	@Override
	public int updateStatusByPrimaryKeyAndStatus(Integer orderId, int nextStatus, int preStatus) {
		System.out.println("执行了方法OrderDaoImpl.updateStatusByPrimaryKeyAndStatus("+orderId+","+nextStatus+","+preStatus+",)");
		return 1;
	}

	
	
	/**
	 * 
	 */
	@Override
	public int updateByPrimaryKeySelective(Order order) {
		System.out.println("执行了方法OrderDaoImpl.updateByPrimaryKeySelective("+order+",)");
		return 1;
	}
	

}
