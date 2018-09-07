package guava.limit.demo;

import guava.limit.model.GoodInfo;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月7日 下午5:21:25
 *
 */
public interface GoodInfoService {

	/**
	 * 
	 * @param code
	 * @param count
	 * @return
	 */
	public int update(String code , int count);
	
	
	/**
	 * 
	 * @param goodInfo
	 * @return
	 */
	public int add(GoodInfo goodInfo);
}
