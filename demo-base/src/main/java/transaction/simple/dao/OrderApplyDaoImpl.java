package transaction.simple.dao;

import org.springframework.stereotype.Component;

import transaction.simple.model.OrderApply;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午4:26:00
 *
 */
@Component
public class OrderApplyDaoImpl implements OrderApplyDao {

	
	/**
	 * 
	 * @param apply
	 * @return
	 */
	public int insert(OrderApply apply){
		System.out.println("执行了方法OrderApplyDaoImpl.insert("+apply+")");
		return 1;
	}
	
	
}
