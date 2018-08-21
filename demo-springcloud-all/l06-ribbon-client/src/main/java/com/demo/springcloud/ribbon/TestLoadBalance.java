package com.demo.springcloud.ribbon;

import java.util.ArrayList;
import java.util.List;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
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
 * @author yuezh2   2018年8月21日 上午11:42:23
 *
 */
public class TestLoadBalance {
	
	
	
	public static void main(String[] args) {
		ILoadBalancer lb = new BaseLoadBalancer();
		List<Server> servers = new ArrayList<>();
		servers.add(new Server("localhost", 8080));
		servers.add(new Server("localhost", 8081));
		
		lb.addServers(servers);
		for(int i = 0 ; i <10 ; i++){
			Server s = lb.chooseServer(null);//默认是轮询规则
			System.out.println(s);
		}
	}

}
