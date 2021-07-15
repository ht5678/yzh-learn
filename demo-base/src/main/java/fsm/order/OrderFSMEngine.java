package fsm.order;

import java.util.HashMap;
import java.util.Map;

import fsm.engine.AbstractFSMEngine;





/**
 * 
 * 类StatusMachine.java的实现描述：状态机 TODO 类实现描述：状态机 
 * @author yuezhihua 2014年10月9日 下午12:30:50
 * @param <T>
 */
public class OrderFSMEngine extends AbstractFSMEngine<OrderStatusEnum,OrderEventEnum>{
	
	
	private final static Map<String, OrderStatusEnum> STATUSFLOW = new HashMap<>(); 
	
	
	public OrderFSMEngine() {
		//============================
		
		//cs
		STATUSFLOW.put(getKey(OrderStatusEnum.CYBERSOURCE_REVIEW, OrderEventEnum.E_P_CYBERSOURCE_REVIEW_SUCCESS), OrderStatusEnum.CYBERSOURCE_PASS);

	}
	
	
	/**
	 * 
	 * @param preStatus
	 * @param event
	 * @return
	 */
	public String getKey(OrderStatusEnum preStatus, OrderEventEnum event) {
		return preStatus.name()+"_"+event.name();
	}
	
	


	@Override
	public OrderStatusEnum getNextStatus(OrderStatusEnum preStatus, OrderEventEnum event) {
		return STATUSFLOW.get(getKey(preStatus, event));
	}


	@Override
	public int getNextStatus(int preStatus, OrderEventEnum event) {
		return STATUSFLOW.get(getKey(OrderStatusEnum.getEnumByValue(preStatus), event)).getValue();
	}
	


}
