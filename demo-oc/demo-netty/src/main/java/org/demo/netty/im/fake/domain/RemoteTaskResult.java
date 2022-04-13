package org.demo.netty.im.fake.domain;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午9:36:41
 */
public class RemoteTaskResult {

	//code默认100成功
	//100 成功 101 无法路由当前节点 102集群不可用 103执行超时 104 任务执行被打断 105任务执行异常 106 未知错误
	private int code;
	
	private String msg;
	
	private Object result;
	
	
	/**
	 * 
	 */
	public RemoteTaskResult() {
		this(100,"success");
	}
	
	
	/**
	 * 
	 */
	public RemoteTaskResult(int code , String msg) {
		this(code,msg , null);
	}
	
	
	
	/**
	 * 
	 * @param code
	 * @param msg
	 * @param result
	 */
	public RemoteTaskResult(int code , String msg , Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}
	
	
	
	
	
}
