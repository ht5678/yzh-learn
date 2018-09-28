package miaosha.mq.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import miaosha.mq.model.TicketInfo;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月12日 下午5:55:29
 *
 */
@Component
public class TicketDaoImpl implements TicketDao {

	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(TicketDaoImpl.class);
	
	@Autowired
	private JdbcTemplate template;
	
	
	private static int count = 1;
	
	
	/**
	 * 
	 */
//	public TicketDaoImpl(){
//		try{
//			ComboPooledDataSource ds = new ComboPooledDataSource();
//			ds.setDriverClass("com.mysql.jdbc.Driver");
//			ds.setMinPoolSize(2);
//			ds.setMaxPoolSize(2);
//			ds.setJdbcUrl("jdbc:mysql://lenovodb:3306/?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true");
//			ds.setUser("myuser");
//			ds.setPassword("mypassword");
//			
//			template = new JdbcTemplate();
//			template.setDataSource(ds);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	
	/**
	 * 
	 * @param ticketid
	 * @return
	 */
	public TicketInfo getTicket(String ticketid){
		TicketInfo ticket = null;
		try{
			ticket = template.query("select * from test.ticket_main where ticketid=?", new Object[]{ticketid} , new ResultSetExtractor<TicketInfo>(){

				@Override
				public TicketInfo extractData(ResultSet rs) throws SQLException, DataAccessException {
					TicketInfo ticket = new TicketInfo();
					while(rs.next()){
						ticket.setTicketid(rs.getString("ticketid"));
						ticket.setBuytime(rs.getLong("buytime"));
						ticket.setTicketmonney(rs.getInt("ticketmoney"));
						ticket.setTicketstatus(rs.getInt("ticketstatus"));
						ticket.setVersion(rs.getInt("version"));
					}
					return ticket;
				}
				
			});
			System.out.println("成功查询一次购买信息:"+count++);
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return ticket;
	}

	
}
