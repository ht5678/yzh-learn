package fsm;

import fsm.order.OrderFSMEngine;

/**
 * 
 * @author yuezh2
 * @date 2020/07/31 15:56
 *
 */
public abstract class FSMEngineFactory {
	
    private volatile static OrderFSMEngine orderFSMEngine;


    /**
     *  订单状态引擎
     * @param param
     * @return
     */
	public static OrderFSMEngine getOrderEngine() {
        if (orderFSMEngine == null) {
            synchronized (OrderFSMEngine.class) {
                if (orderFSMEngine == null) {
                	orderFSMEngine = new OrderFSMEngine();
                }
            }
        }
		return orderFSMEngine;
	}
	


}
