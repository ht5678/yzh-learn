package transaction.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Connection.TRANSACTION_READ_UNCOMMITTED
 * 允许读取未提交事务
 * 
 * account:
 * id , accountName , user , money
 * 
 * user:
 * id , name , createTime
 * 
 * 
 * 结果:
 * 	 	执行插入
 *		执行查询
 *	 	8,1111,zhangsan,10000,
 * 
 * 查询出来结果,但是数据库表中并没有数据,因为autocommit为false
 * 
 * 
 * @author yuezh2   2019年10月3日 下午10:19:41
 *
 */
public class ReadUncommittedExample {

	static{
		try {
			openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static Connection openConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://10.250.5.13:3306/test","root","12345");
		return conn;
	}
	
	
	
	public static void insert(String accountName , String name , int money){
		Connection conn;
		try {
			conn = openConnection();
			conn.setAutoCommit(false);
			PreparedStatement prepare = conn.prepareStatement("insert into account(accountName,user,money) values (?,?,?)");
			prepare.setString(1, accountName);
			prepare.setString(2, name);
			prepare.setInt(3, money);
			prepare.executeUpdate();
			System.out.println("执行插入");
			Thread.sleep(3000);
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void select(String name , Connection conn){
		PreparedStatement prepare;
		try {
			prepare = conn.prepareStatement("select * from account where user=?");
			prepare.setString(1, name);
			ResultSet resultSet = prepare.executeQuery();
			System.out.println("执行查询");
			while(resultSet.next()){
				for(int i = 1 ; i <= 4 ; i++){
					System.out.print(resultSet.getString(i)+",");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	public static Thread run(Runnable runnable){
		Thread thread1 = new Thread(runnable);
		thread1.start();
		return thread1;
	}
	
	
	
	public static void main(String[] args) {
		//启动插入线程
		Thread t1 = run(new Runnable() {
			
			@Override
			public void run() {
				insert("1111", "zhangsan", 10000);
			}
		});
		
		
		//启动查询线程
		Thread t2 = run(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					Connection conn = openConnection();
					//将参数升级成Connection.TRANSACTION_READ_COMMITTED 即可解决脏读的问题
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					select("zhangsan", conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
