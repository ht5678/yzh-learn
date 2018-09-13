package miaosha.mq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miaosha.mq.dao.TicketDao;
import miaosha.mq.model.TicketInfo;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:15:13
 *
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;
	
	
	/**
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicket(String ticketid){
		return ticketDao.getTicket(ticketid);
	}
	
}
