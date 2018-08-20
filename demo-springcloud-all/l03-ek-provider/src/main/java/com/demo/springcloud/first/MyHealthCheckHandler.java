package com.demo.springcloud.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;



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
 * 通知其他的监听服务器 , 比如eureka , 每30s执行一次
 * 
 * @author yuezh2   2018年8月20日 下午8:35:54
 *
 */
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {

	@Autowired
	private MyHealthIndicator healthIndicator;
	
	
	@Override
	public InstanceStatus getStatus(InstanceStatus arg0) {
		Status status = healthIndicator.health().getStatus();
		if(status.equals(Status.UP)){
			return InstanceStatus.UP;
		}else{
			return InstanceStatus.DOWN;
		}
	}

}
