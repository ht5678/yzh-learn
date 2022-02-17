package com.demo.dfs.client;



/**
 * 作为文件系统的接口
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:26:43
 */
public interface FileSystem {

	
	/**
	 * 创建目录
	 * @param path
	 * @throws Exception
	 */
	void mkdir(String path) throws Exception;
	
	
	/**
	 * 优雅关闭
	 */
	void shutdown() throws Exception;
	
}
