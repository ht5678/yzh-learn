package miaosha.mq.service;

import miaosha.mq.model.TicketInfo;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:15:00
 *
 */
public interface TicketService {
	
	/**
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicket(String ticketid);

}
