package org.dfs.namenode;

import java.io.File;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午8:57:21
 */
public class FSNamesystem {

	
	
	public Boolean mkdir(String path) {
		File dir = new File(path);
		return dir.mkdirs();
	}

}
