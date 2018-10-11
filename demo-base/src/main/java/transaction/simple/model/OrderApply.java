package transaction.simple.model;


/**
 * 
 * 流水单  ,  对于银行系统没有做幂等性校验 , 如果每次都传  订单号(orderId) 的话 , 会返回错误 , 因为银行数据库是订单号主键唯一索引 ;
 * 所以加了一个流水单号 , 每次变更  ,  这样就把银行的幂等性校验 转换到 咱们系统内部实现解决幂等性校验了 , 
 * 
 * @author yuezh2   2018年10月11日 下午4:47:00
 *
 */
public class OrderApply {

	private String orderId;
	
	private int status;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
