package org.dfs.client;

import com.demo.dfs.client.FileSystem;
import com.demo.dfs.client.FileSystemImpl;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:43:28
 */
public class TestFileSystem {

	public static void main(String[] args) throws Exception{
		
		FileSystem fileSystem = new FileSystemImpl();
		
		
		//test1
//		fileSystem.mkdir("d:/testdfs/data");
//		fileSystem.mkdir("d:/testdfs/data1");
//		fileSystem.mkdir("d:/testdfs/data2");
		
		
		//test2
		for(int j = 0 ; j < 4 ; j++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						for(int i = 0 ; i < 200 ; i ++) {
							fileSystem.mkdir("d:/testdfs/data"+i+"_"+Thread.currentThread().getName());
						}
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();;
		}
		
		
		
		//test2
//		fileSystem.shutdown();
		
	}
	
}
