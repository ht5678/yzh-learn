package zookeeper.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;

/**
 * zk服务器节点的配置信息
 * @author yuezh2   2016年4月29日 下午7:55:53
 *
 */
public class ZooKeeperRegistContext {

	private String path;
	
	private ZkClient zkClient;
	
	private Object data;
	
	
	public ZooKeeperRegistContext(String path , ZkClient zkClient , Object data){
		super();
		this.path = path;
		this.zkClient = zkClient;
		this.data = data;
	}
	
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
