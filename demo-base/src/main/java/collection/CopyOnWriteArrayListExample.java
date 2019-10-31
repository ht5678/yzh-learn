package collection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年10月31日 下午4:51:07  
 *
 */
public class CopyOnWriteArrayListExample {

	
	public static void main(String[] args) throws Exception{
		final List<Integer> list = new CopyOnWriteArrayList<>();
		
		//线程A将0-1000添加到list
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0 ; i < 1000; i ++) {
					list.add(i);
				}
			}
		}).start();
		
		
		//线程B将1000-2000添加到列表
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i =  1000 ; i < 2000;i++) {
					list.add(i);
				}
			}
		}).start();
		
		
		Thread.sleep(2000);
		
		//打印所有结果
		for(int i = 0 ; i < list.size() ; i ++) {
			System.out.println("第" + (i + 1) + "个元素为：" + list.get(i));
		}
		
	}
	
	
}
