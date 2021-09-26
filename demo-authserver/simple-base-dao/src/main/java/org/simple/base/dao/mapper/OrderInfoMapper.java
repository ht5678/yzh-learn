package org.simple.base.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.simple.base.dao.entity.OrderInfo;

/**
 * 
 * @author yue
 *
 */
public interface OrderInfoMapper {

	public OrderInfo selectOrderInfoByIdAndUserName(@Param("orderNo") String orderNo , @Param("userName")String userName);
	
	public int insertOrder(OrderInfo orderInfo);
	
}
