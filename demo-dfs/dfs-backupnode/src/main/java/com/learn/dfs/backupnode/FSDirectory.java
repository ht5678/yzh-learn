package com.learn.dfs.backupnode;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.alibaba.fastjson.JSONObject;

/**
 * 管理内存中的文件目录树的核心组件
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:53:13
 */
public class FSDirectory {

	private INodeDirectory dirTree ;
	
	/**
	 * 当前文件目录树的更新到了哪个txid对应的editslog
	 */
	private long maxTxid = 0L;
	
	/**
	 * 文件目录树的读写锁
	 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	

	/**
	 * 读写锁加锁和释放锁
	 */
	public void writeLock() {
		lock.writeLock().lock();
	}
	
	public void writeUnLock() {
		lock.writeLock().unlock();
	}
	
	public void readLock() {
		lock.readLock().lock();
	}
	
	public void readUnLock() {
		lock.readLock().unlock();
	}
	
	
	
	public FSDirectory () {
		this.dirTree = new INodeDirectory("/");
	}
	
	
	
	/**
	 * 以json格式获取到fsimage内存元数据
	 * @return
	 */
	public FsImage getFsImageJson() {
		FsImage fsimage = null;
		try {
			readLock();
			
			String fsImageJson = JSONObject.toJSONString(dirTree);
			//在这个时候 ， 我们还需要知道 ， 当前这份元数据 ， 同步到的最大txid是多少
			//这样 ， 我们才知道 ， 这个fsimage对应着txid等于多少的editslog , 在这个txid之前的editslog都可以不需要了
			//都可以删除了
			fsimage = new FsImage(maxTxid, fsImageJson);
		}finally {
			readUnLock();
		}
		return fsimage;
	}
	
	
	/**
	 * 创建目录
	 * @param path
	 */
	public void mkdir(long txid , String path) {
		//path = /usr/local/hive
		//应该先判断一下 , "/"根目录下有没有一个  usr  目录的存在
		//每级目录都需要创建 , 最后对 "/hive" 这个目录创建一个节点挂载上去
//		synchronized (dirTree) {
		try {
			
			writeLock();
			
			maxTxid = txid;
			
			String[] pathes = path.split("/");
			INodeDirectory parent = dirTree;
			
			for(String splitedPath : pathes) {
				if(splitedPath.trim().equals("")) {
					continue;
				}
				
				INodeDirectory dir = findDirectory(parent , splitedPath);
				if(dir != null) {
					parent = dir;
					continue;
				}
				
				INodeDirectory child = new INodeDirectory(splitedPath);
				parent.addChild(child);
				parent = child;
				
				
			}
			
			//
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}finally {
			writeUnLock();
		}
//		System.out.println(dirTree.toString());
//		printDirTree(dirTree , "  ");
	}
	
	
	/**
	 * 
	 * @param dirTree
	 */
	@SuppressWarnings("unused")
	private void printDirTree(INodeDirectory dirTree , String blank){
		if(dirTree.getChildren().size() == 0){
			return ;
		}
		
		for(INode dir : dirTree.getChildren()){
			System.out.println(blank + ((INodeDirectory)dir).getPath());
			printDirTree(((INodeDirectory)dir) , blank + blank);
		}
	}
	
	
	/**
	 * 对文件目录树递归查找目录
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:02:34
	 */
	private INodeDirectory findDirectory(INodeDirectory dir , String path) {
		if(dir.getChildren().size() == 0) {
			return null;
		}
		
//		INodeDirectory resultDir = null;
		
		for(INode child : dir.getChildren()) {
			if(child instanceof INodeDirectory) {
				INodeDirectory childDir = (INodeDirectory)child;
				
				if((childDir.getPath().equals(path))) {
					return childDir;
				}
				
				//移除递归
//				resultDir = findDirectory(childDir, path);
//				if(resultDir != null) {
//					return resultDir;
//				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 代表的文件目录树中的一个节点
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:07:12
	 */
	private interface INode{
	}
	
	
	/**
	 * 代表文件目录树中的一个目录
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:07:27
	 */
	public static class INodeDirectory implements INode{
		
		private List<INode> children;
		private String path;
		
		public INodeDirectory(String path) {
			this.path = path;
			this.children = new LinkedList<>();
		}
		
		public void addChild(INode inode) {
			this.children.add(inode);
		}


		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public void setChildren(List<INode> children) {
			this.children = children;
		}

		public List<INode> getChildren() {
			return children;
		}

		@Override
		public String toString() {
			return "INodeDirectory [children=" + children + ", path=" + path + "]";
		}

		
	}
	
	
	
	/**
	 * 代表文件目录树中的一个文件
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月30日下午3:11:07
	 */
	public static class INodeFile implements INode{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "INodeFile [name=" + name + "]";
		}
		
	}
	

}
