package miaosha.mq.model;



/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:17:14
 *
 */
public class TicketInfo {

	private String ticketid;
	
	private long buytime;
	
	private int ticketmonney;
	
	private int ticketstatus;
	
	private int version;

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public long getBuytime() {
		return buytime;
	}

	public void setBuytime(long buytime) {
		this.buytime = buytime;
	}

	public int getTicketmonney() {
		return ticketmonney;
	}

	public void setTicketmonney(int ticketmonney) {
		this.ticketmonney = ticketmonney;
	}

	public int getTicketstatus() {
		return ticketstatus;
	}

	public void setTicketstatus(int ticketstatus) {
		this.ticketstatus = ticketstatus;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
	

}
