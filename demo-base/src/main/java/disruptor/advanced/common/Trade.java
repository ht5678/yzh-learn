package disruptor.advanced.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月19日下午5:10:06
 */
public class Trade {

	private String id;
	
	private String name;
	
	private double price;
	
	private AtomicInteger count = new AtomicInteger(0);

	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public AtomicInteger getCount() {
		return count;
	}

	public void setCount(AtomicInteger count) {
		this.count = count;
	}

	
	
	
	@Override
	public String toString() {
		return "Trade [id=" + id + ", name=" + name + ", price=" + price + ", count=" + count + "]";
	}
	
	
}
