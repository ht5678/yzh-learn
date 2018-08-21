package com.demo.springcloud.ribbon;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;



/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年8月21日 上午11:49:11
 *
 */
public class MyRule implements IRule {
	
	private ILoadBalancer lb;

	@Override
	public Server choose(Object key) {
		Random r = new Random();
		int rNum = r.nextInt(10);
		
		List<Server> servers = lb.getAllServers();
		if(rNum>7){
			return getServerByPort(servers, 8080);
		}else{
			return getServerByPort(servers, 8081);
		}
	}
	
	
	
	private Server getServerByPort(List<Server> servers , int port){
		for(Server server : servers){
			if(server.getPort()==port){
				return server;
			}
		}
		return null;
	}
	
	

	@Override
	public ILoadBalancer getLoadBalancer() {
		return lb;
	}

	@Override
	public void setLoadBalancer(ILoadBalancer arg0) {
		this.lb = arg0;
	}

}
