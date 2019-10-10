package transaction.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * spring编程事务示例
 * 
 * 
 * savepoint练习
 * 
 * @author yuezh2   2019年10月4日 下午10:48:32
 *
 */
public class SpringTransactionExample {
	
	private static String url = "jdbc:mysql://10.250.5.13:3306/test";
	private static String user = "root";
	private static String password = "12345";
	
	
	
	public static Connection openConnection()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
	
	
	
	public static void main(String[] args) {
		final DataSource ds = new DriverManagerDataSource(url,user,password);
		
		final TransactionTemplate template = new TransactionTemplate();
		
		template.setTransactionManager(new DataSourceTransactionManager(ds));
		
		template.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				Connection conn = DataSourceUtils.getConnection(ds);
//				conn.setAutoCommit(false);
				Object savePoint = null;
				try{
					{
						//插入
						PreparedStatement prepare = conn.prepareStatement("insert into account(accountName,user, money) values (?,?,?)");
						prepare.setString(1, "111");
						prepare.setString(2, "aaa");
						prepare.setInt(3, 10000);
						prepare.executeUpdate();
					}
					
					//设置保存点
					savePoint = status.createSavepoint();
					
					{
						//插入
						PreparedStatement prepare = conn.prepareStatement("insert into account(accountName,user,money) values (?,?,?)");
						prepare.setString(1, "222");
						prepare.setString(2, "bbb");
						prepare.setInt(3, 10000);
						prepare.executeUpdate();
					}
					
					{
						//更新
						PreparedStatement prepare = conn.prepareStatement("update account set money=money+1 where user=?");
						prepare.setString(1, "asdfkjaf");
						
						//故意制造异常
						int i = 1/0;
					}
//					conn.commit();
				}catch(Exception e){
					e.printStackTrace();
					
					System.out.println("更新失败");
					if(savePoint!=null){
						status.rollbackToSavepoint(savePoint);
					}else{
						status.setRollbackOnly();
					}
				}
				return null;
			}
			
		});
		
		
	}
	
			
	
	
	

}
