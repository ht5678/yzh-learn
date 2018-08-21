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
 * @author yuezh2   2018年8月21日 下午2:54:59
 *
 */
public class TestMyRuleLB {
	
	
	public static void main(String[] args) {
		BaseLoadBalancer lb = new BaseLoadBalancer();
		List<Server> servers = new ArrayList<>();
		servers.add(new Server("localhost", 8080));
		servers.add(new Server("localhost", 8081));
		
		MyRule mr = new MyRule();
		mr.setLoadBalancer(lb);
		lb.setRule(mr);
		
		lb.addServers(servers);
		for(int i = 0 ; i <10 ; i++){
			Server s = lb.chooseServer(null);//默认是轮询规则
			System.out.println(s);
		}
	}

}
