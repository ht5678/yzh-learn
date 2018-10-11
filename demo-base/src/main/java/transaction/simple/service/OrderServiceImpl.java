package transaction.simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import transaction.simple.dao.OrderApplyDao;
import transaction.simple.dao.OrderDao;
import transaction.simple.model.Constant;
import transaction.simple.model.Order;
import transaction.simple.model.OrderApply;

/**
 * 
 * @author sdwhy
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private OrderApplyDao orderApplyDao;
	

	
	/**
	 * 
	 * #####订单支付
	 * 
	 * 数据库锁:
	 * 		悲观锁:	select * from order where id=? for update
	 * 		乐观锁:	基于版本号来实现:update order set status=? where id=? and version=?
	 * 					基于状态机实现:	  update order set status=3 where id=? and status=0
	 * 
	 */
	@Transactional(propagation=Propagation.NEVER)
	@Override
	public void pay(final Order order) {
		try{
			//获取分布式锁
			boolean lockStatus = transactionTemplate.execute(new TransactionCallback<Boolean>() {

				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					//基于状态机实现 , update order set status=3 where id=? and status=0
					int result = orderDao.updateStatusByPrimaryKeyAndStatus(order.getId(), Constant.ORDER_STATUS_DEALING, Constant.ORDER_STATUS_DEFAULT);
					
					if(1 == result){
						OrderApply apply = new OrderApply();
						
					}
					
					return null;
				}
				
			});
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
