package fsm.order;


/**
 * 
 * @author yuezh2
 * @date 2020/06/17 15:33
 *
 */
public enum OrderStatusEnum {

	
	CYBERSOURCE_REVIEW(601),
	CYBERSOURCE_PASS(602),
	
	;
    
    
    
    
    private int value;
    
    private OrderStatusEnum(int value) {
    	this.value = value;
    }

	public int getValue() {
		return value;
	}

	
	public static OrderStatusEnum getEnumByValue(int ordinal) {
		for(OrderStatusEnum status : OrderStatusEnum.values()) {
			if(ordinal == status.value) {
				return status;
			}
		}
		return null;
	}
	
}
