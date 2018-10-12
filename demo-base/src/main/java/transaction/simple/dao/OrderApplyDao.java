package transaction.simple.dao;

import transaction.simple.model.OrderApply;

public interface OrderApplyDao {
	
	
	/**
	 * 
	 * @param apply
	 * @return
	 */
	public int updateByPrimaryKeySelective(OrderApply apply);
	
	
	/**
	 * 
	 * @param apply
	 * @return
	 */
	public int insert(OrderApply apply);

}
