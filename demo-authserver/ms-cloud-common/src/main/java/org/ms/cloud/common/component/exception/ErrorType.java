package org.ms.cloud.common.component.exception;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午9:30:23
 */
public interface ErrorType {

	/**
	 * 返回code
	 * @return
	 */
	public String getCode();
	
	
	/**
	 * 返回msg
	 * @return
	 */
	public String getMsg();
	
}
