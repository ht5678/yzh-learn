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
			Integer applyId = transactionTemplate.execute(new TransactionCallback<Integer>() {

				@Override
				public Integer doInTransaction(TransactionStatus status) {
					//基于状态机实现 , update order set status=3 where id=? and status=0
					boolean lockStatus = 1 == orderDao.updateStatusByPrimaryKeyAndStatus(order.getId(), Constant.ORDER_STATUS_DEALING, Constant.ORDER_STATUS_DEFAULT);
					
					if(lockStatus){
						OrderApply apply = new OrderApply();
						apply.setOrderId(order.getId());
						apply.setStatus(1);		//1表示未处理
						orderApplyDao.insert(apply);
						//TODO
//						return apply.getOrderId();
					}
					//没有获得锁,返回-1
					return -1;
				}
				
			});
			
			
			//获得了锁
			if(applyId>0){
				Order bankOrder = new Order();
//				bankOrder.setId(applyId);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
