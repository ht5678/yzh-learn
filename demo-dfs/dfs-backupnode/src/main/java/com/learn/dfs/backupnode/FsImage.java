package com.learn.dfs.backupnode;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月2日 下午9:59:42
 */
public class FsImage {

	private long maxTxid;
	
	private String fsImageJson;

	
	
	
	public FsImage(long maxTxid, String fsImageJson) {
		super();
		this.maxTxid = maxTxid;
		this.fsImageJson = fsImageJson;
	}

	
	
	
	public long getMaxTxid() {
		return maxTxid;
	}

	public void setMaxTxid(long maxTxid) {
		this.maxTxid = maxTxid;
	}

	public String getFsImageJson() {
		return fsImageJson;
	}

	public void setFsImageJson(String fsImageJson) {
		this.fsImageJson = fsImageJson;
	}
	
	
	
	
}
