package transaction.simple.dao;



/**
 * 
 * 
 * 
 * @author yuezh2   2018年10月11日 下午3:58:36
 *
 */
public interface OrderDao {
	
	/**
	 * 
	 * @return
	 */
	public int updateStatusByPrimaryKeyAndStatus(String orderId , int nextStatus  , int preStatus);

}
