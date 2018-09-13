package miaosha.mq.dao;

import miaosha.mq.model.TicketInfo;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:55:17
 *
 */
public interface TicketDao {
	
	/**
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicket(String ticketid);

}
