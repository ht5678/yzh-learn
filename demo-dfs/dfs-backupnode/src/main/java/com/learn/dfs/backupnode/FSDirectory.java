package com.learn.dfs.backupnode;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 管理内存中的文件目录树的核心组件
 * @author yuezh2@lenovo.com
 *	@date 2021年12月30日下午2:53:13
 */
public class FSDirectory {

	private INodeDirectory dirTree ;
	
	
	
	
	public FSDirectory () {
		this.dirTree = new INodeDirectory("/");
	}
	
	
	/**
	 * 创建目录
	 * @param path
	 */
	public void mkdir(String path) {
		//path = /usr/local/hive
		//应该先判断一下 , "/"根目录下有没有一个  usr  目录的存在
		//每级目录都需要创建 , 最后对 "/hive" 这个目录创建一个节点挂载上去
		synchronized (dirTree) {
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
