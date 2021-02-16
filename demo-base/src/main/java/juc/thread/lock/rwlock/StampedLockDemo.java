package juc.thread.lock.rwlock;

import java.util.concurrent.locks.StampedLock;

/**
 * 
 * @author yuezh2
 *
 * @date 2021年2月16日 下午3:00:30  
 *
 */
public class StampedLockDemo {

	private double x,y;
	
	private final StampedLock sl = new StampedLock();
	
	
	/**
	 * 血锁经典使用方法
	 * @param deltaX
	 * @param deltaY
	 */
	public void move(double deltaX , double deltaY) {
		//获取写锁 , 返回一个版本号 (戳)
		long stamp = sl.writeLock();
		try {
			y += deltaY;
			x +=deltaX;
		}finally {
			//释放写锁 , 需要传入上面获取的版本号
			sl.unlockWrite(stamp);
		}
	}
	
	
	/**
	 * 乐观锁转悲观锁 , 乐观锁不需要释放?cas
	 * @return
	 */
	public double distanceFromOrigin() {
		//乐观读
		long stamp = sl.tryOptimisticRead();
		double currentX = x , currentY = y;
		//验证版本号是否有变化
		if(!sl.validate(stamp)) {
			//版本号变了 , 乐观锁变悲观锁
			stamp = sl.readLock();
			try {
				//重新读取x , y 的值
				currentX = x;
				currentY = y;
			} finally {
				//释放读锁 , 需要传入上面的版本号
				sl.unlockRead(stamp);
			}
		}
		
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}
	
	
	/**
	 * 读锁升级成写锁
	 * @param newX
	 * @param newY
	 */
	public void moveIfAtOrigin(double newX , double newY) {
		//获取悲观读锁
		long stamp = sl.readLock();
		try {
			while(x== 0.0 && y == 0.0) {
				//转为写锁 
				long ws = sl.tryConvertToWriteLock(stamp);
				//转换成功
				if(ws!=0L) {
					stamp = ws;
					x = newX;
					y = newY;
					break;
				}else {
					//转换失败
					sl.unlockRead(stamp);
					//获取写锁
					stamp = sl.writeLock();
				}
			}
		}finally {
			//释放锁
			sl.unlock(stamp);
		}
	}
	
	
}
