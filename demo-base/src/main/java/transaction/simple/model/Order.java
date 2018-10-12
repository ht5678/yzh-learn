package transaction.simple.model;

import java.math.BigDecimal;

/**
 * 
 * @author sdwhy
 *
 */
public class Order {

	//唯一订单号
	private Integer id;
	//金额
	private BigDecimal amount;
	//其他属性
	private String name;
	
	private int status;
	
	
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	@Override
	public String toString() {
		return "OrderVO [id=" + id + ", amount=" + amount + ", name=" + name + "]";
	}
	
	
}
