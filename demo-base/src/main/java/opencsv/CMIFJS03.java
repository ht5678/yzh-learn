package opencsv;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

/**
 * 
 * No.011170919107609,5,セット値引き(仮称),1000,1,1000,0,
 * 
 * @author yuezh2
 *
 */
public class CMIFJS03 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/* NEC Direct注文番号 */
	//No.011170919107609
	@CsvBindByPosition(position=0)
	@CsvBindByName(column = "NEC Direct注文番号(NEC Direct sales order id)")
	private String orderId;
	/* NEC Direct　項番 */
	//5
	@CsvBindByPosition(position=1)
	@CsvBindByName(column = "NEC Direct　項番(NEC Direct Detail id)")
	private String orderDetailNumber;
	/* 名称 */
	//セット値引き(仮称)
	@CsvBindByPosition(position=2)
	@CsvBindByName(column = "名称(Discount name)")
	private String discountName;
	/* 値引き単価 */
	//1000
	@CsvBindByPosition(position=3)
	@CsvBindByName(column = "値引き単価(Discount price)")
	private String discountAmount;
	/* 値引き数 */
	//1
	@CsvBindByPosition(position=4)
	@CsvBindByName(column = "値引き数(Discount quantity)")
	private String discountQty;
	/* 値引き合価 */
	//1000
	@CsvBindByPosition(position=5)
	@CsvBindByName(column = "値引き合価(Discount amount)")
	private String discountAllAmount;
	/* 値引き後価格 */
	//0
	@CsvBindByPosition(position=6)
	@CsvBindByName(column = "値引き後価格(After discount amount)")
	private String afterDiscountProdcuctAmount;
	/* クーポン番号 */
	@CsvBindByPosition(position=7)
	@CsvBindByName(column = "クーポン番号(eCoupon id)")
	private String couponCode;

	
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDetailNumber() {
		return orderDetailNumber;
	}

	public void setOrderDetailNumber(String orderDetailNumber) {
		this.orderDetailNumber = orderDetailNumber;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDiscountQty() {
		return discountQty;
	}

	public void setDiscountQty(String discountQty) {
		this.discountQty = discountQty;
	}

	public String getDiscountAllAmount() {
		return discountAllAmount;
	}

	public void setDiscountAllAmount(String discountAllAmount) {
		this.discountAllAmount = discountAllAmount;
	}

	public String getAfterDiscountProdcuctAmount() {
		return afterDiscountProdcuctAmount;
	}

	public void setAfterDiscountProdcuctAmount(String afterDiscountProdcuctAmount) {
		this.afterDiscountProdcuctAmount = afterDiscountProdcuctAmount;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	


}
