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
import transaction.simple.model.RespEntity;

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
			final Integer applyId = transactionTemplate.execute(new TransactionCallback<Integer>() {

				@Override
				public Integer doInTransaction(TransactionStatus status) {
					//基于状态机实现 , update order set status=3 where id=? and status=0
					boolean lockStatus = 1 == orderDao.updateStatusByPrimaryKeyAndStatus(order.getId(), Constant.ORDER_STATUS_DEALING, Constant.ORDER_STATUS_DEFAULT);
					
					if(lockStatus){
						OrderApply apply = new OrderApply();
						apply.setOrderId(order.getId());
						apply.setStatus(1);		//1表示未处理
						orderApplyDao.insert(apply);
						return apply.getOrderId();
					} 
					//没有获得锁,返回-1
					return -1;
				}
				
			});
			
			
			//获得了锁
			if(applyId>0){
				final Order bankOrder = new Order();
				bankOrder.setId(applyId);			//扣款申请的流水号
				//调用远程银行服务(扣款)
				RespEntity<Object> respEntity = bankService.outMoney(bankOrder);
				//替代方案
				if(respEntity!=null){
					if("0000".equals(respEntity.getKey())){
						//交易成功
						order.setStatus(Constant.ORDER_STATUS_SUCCESS);
					}else{
						//交易失败
						order.setStatus(Constant.ORDER_STATUS_FAIL);
					}
				}
				
				transactionTemplate.execute(new TransactionCallback<Object>() {

					@Override
					public Object doInTransaction(TransactionStatus status) {
						//更新状态
						orderDao.updateByPrimaryKeySelective(bankOrder);
						
						OrderApply apply = new OrderApply();
						apply.setOrderId(applyId);
						apply.setStatus(order.getStatus());		//跟订单状态一样
						
						orderApplyDao.updateByPrimaryKeySelective(apply);
						return null;
					}
					
				});
				
			}else{
				System.out.println("锁失败了");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
