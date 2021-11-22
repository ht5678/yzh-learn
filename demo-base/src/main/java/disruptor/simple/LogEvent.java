package disruptor.simple;


/**
 * 定义事件，事件工厂
 * @author yuezh2@lenovo.com
 *	@date 2021年11月18日下午8:59:39
 */
public class LogEvent {
	
	private String msg;

	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "LogEvent [msg=" + msg + "]";
	}
	
	
	/**
	 * 
	 */
	public void clear(){
		msg = null;
    }
}
