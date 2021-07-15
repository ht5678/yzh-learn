package fsm.engine;


/**
 * 
 * @author yuezh2
 * @date 2020/06/17 16:40
 *
 */
public interface SimpleStatusCallback<T> {

	/**
	 * 监听操作
	 * @param orderStatus
	 * @param event
	 * @return
	 */
	public int listener(T status);
	
	
}
