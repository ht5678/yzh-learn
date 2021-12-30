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
		fileSystem.mkdir("d:/testdfs/data");
	}
	
}
