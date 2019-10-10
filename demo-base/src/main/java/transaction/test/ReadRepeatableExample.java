package transaction.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Connection.TRANSACTION_REPEATABLE_READ
 * 可重复读,在一个事务中同一sql语句,无论执行多少次都会得到相同的结果
 * 
 * 
 * 在执行之前,需要插入一条数据:
 * INSERT INTO `account` VALUES (11, '11', 'zhangsan', 10000);
 * 
 * 
 * TRANSACTION_READ_COMMITTED结果:
 * 	执行查询
 *	11,11,zhangsan,10000,
 *	执行修改成功
 *	执行查询
 *	11,11,zhangsan,10001,
 *
 *
 * TRANSACTION_REPEATABLE_READ结果(更高的级别):
 * 	执行查询
 *		11,11,zhangsan,10004,
 *		执行修改成功
 *		执行查询
 *		11,11,zhangsan,10004,
 *
 * 
 * @author yuezh2   2019年10月4日 下午9:47:45
 *
 */
public class ReadRepeatableExample {
	
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
	
	
	
	static Object lock = new Object();
	
	
	public static Connection openConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://10.250.5.13:3306/test","root","12345");
		return conn;
	}
	
	
	
	public static void update(String user){
		try{
			Connection conn = openConnection();
			PreparedStatement prepare = conn.prepareStatement("update account set money = money+1 where user=?");
			prepare.setString(1, user);
			prepare.executeUpdate();
			conn.close();
			System.out.println("执行修改成功");
		}catch(Exception e){
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
				
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				update("zhangsan");
			}
		});
		
		
		//启动查询线程
		Thread t2 = run(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					Connection conn = openConnection();
					conn.setAutoCommit(false);
					//将参数升级成Connection.TRANSACTION_READ_COMMITTED 即可解决脏读的问题
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					//第一次读取不到
					select("zhangsan", conn);
					
					//释放锁
					synchronized (lock) {
						lock.notify();
					}
					
					//第二次读取到(数据不一致)
					Thread.sleep(500);
					
					
					select("zhangsan", conn);
					conn.close();
					
					
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
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
