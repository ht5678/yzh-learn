package fsm.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类StatusMachine.java的实现描述：状态机 TODO 类实现描述：状态机 
 * @author yuezhihua 2014年10月9日 下午12:30:50
 */
public abstract class AbstractFSMEngine<S,E> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFSMEngine.class);
	
	/**
	 * 
	 * @param preStatus
	 * @param event
	 * @return
	 */
	public abstract S getNextStatus(S preStatus, E event); 
	
	/**
	 * 
	 * @param preStatus
	 * @param event
	 * @return
	 */
	public abstract int getNextStatus(int preStatus, E event); 
	
	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public S execute(Object id , S preStatus, E event, SimpleStatusCallback<S> callback) {
		S nextStatus = getNextStatus(preStatus, event);
		if(nextStatus!=null) {
			// distributed lock
			callback.listener(nextStatus);
			//filter
			return nextStatus;
		}
		// 其他
		else {
			LOGGER.error("staus engine cannot process next status , preStatus : {} , event : {} " , preStatus , event);
			throw new RuntimeException("staus engine cannot process next status , preStatus【"+preStatus+"】,event【"+event+"】");
		}
	}
	
	
	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public int execute(Object id , int preStatus, E event, IntStatusCallback callback) {
		try {
			int nextStatus = getNextStatus(preStatus, event);
			// distributed lock
			callback.listener(nextStatus);
			//filter
			return nextStatus;
		}catch(Exception e) {
			LOGGER.error(String.format("stats machine error : %s", e.getMessage()) ,e);
			throw new RuntimeException("staus engine cannot process next status , preStatus【"+preStatus+"】,event【"+event+"】");
		}
	}
	
	
	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public S execute(Object id , S preStatus, E event) {
		S nextStatus = execute(id , preStatus, event , simpleCallback);
		
		return nextStatus;
	}
	
	
	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public S execute(S preStatus, E event) {
		S nextStatus = execute(null , preStatus, event , simpleCallback);
		
		return nextStatus;
	}

	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public int execute(Object id , int preStatus, E event) {
		int nextStatus = execute(id , preStatus, event , intCallback);
		
		return nextStatus;
	}
	
	
	/**
	 * 执行状态机
	 * @param preStatus 前状态
	 * @param operation 操作
	 * @return 后状态
	 * @throws Exception 非法操作异常
	 */
	public int execute(int preStatus, E event) {
		int nextStatus = execute(null , preStatus, event , intCallback);
		
		return nextStatus;
	}	
	
	
	private SimpleStatusCallback<S> simpleCallback = new SimpleStatusCallback<S>() {
		
		@Override
		public int listener(S status) {
			//nothing
			return 0;
		}

	};
	
	
	private IntStatusCallback intCallback = new IntStatusCallback() {
		
		@Override
		public int listener(int status) {
			//nothing
			return 0;
		}
	};

}
