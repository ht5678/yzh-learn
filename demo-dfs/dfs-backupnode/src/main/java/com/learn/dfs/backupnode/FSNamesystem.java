package com.learn.dfs.backupnode;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午8:57:21
 */
public class FSNamesystem {
	
	/**
	 * 负责管理内存文件目录树的组件
	 * 
	 * 专门管理维护内存中的文件目录树
	 * 
	 */
	private FSDirectory directory;
	
//	/**
//	 * 负责管理edits log写入磁盘的组件
//	 */
//	private FSEditlog editlog;
	
	
	/**
	 * 
	 */
	public FSNamesystem () {
		this.directory = new FSDirectory();
//		this.editlog = new FSEditlog();
	}

	
	/**
	 * 创建目录
	 * @param path
	 * @return
	 */
	public Boolean mkdir(long txid , String path) {
		this.directory.mkdir(txid , path);
//		this.editlog.logEdit("创建了一个目录 : "+path);
//		this.editlog.logEdit("{'OP':'MKDIR','PATH':'"+path+"'}");
		return true;
		
//		File dir = new File(path);
//		return dir.mkdirs();
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public FsImage getFsImage() throws Exception{
		return directory.getFsImageJson();
	}
	
	
	
	/**
	 * 强制把内存里的edits log刷入磁盘中
	 */
	public void flush() {
//		this.editlog.flush();
	}
	

}
