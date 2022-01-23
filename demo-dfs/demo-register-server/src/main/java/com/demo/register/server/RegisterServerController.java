package com.demo.register.server;

import java.util.Map;

/**
 * 这个controller负责接收register-client发送过来的请求
 * 在Spring Cloud Eureka中用的组件是jersey  , 
 * 在国外常用jersey , restful框架 , 可以接收http请求
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:31:58
 */
public class RegisterServerController {

	private ServiceRegistry registry = ServiceRegistry.getInstance();
	
	
	/**
	 * 服务注册
	 * @param request
	 * @return
	 */
	public RegisterResponse register(RegisterRequest request) {
		RegisterResponse registerResponse = new RegisterResponse();
		try {
			//在注册表中加入这个服务实例
			ServiceInstance serviceInstance = new ServiceInstance();
			serviceInstance.setHostname(request.getHostname());
			serviceInstance.setIp(request.getIp());
			serviceInstance.setPort(request.getPort());
			serviceInstance.setServiceInstanceId(request.getServiceInstanceId());
			serviceInstance.setServiceName(request.getServiceName());
//			serviceInstance.setLease(new Lease());

			registry.register(serviceInstance);
			
			//更新自我保护机制的阈值
			synchronized (SelfProtectionPolicy.class) {
				SelfProtectionPolicy selfProtectionPolicy = SelfProtectionPolicy.getInstance();
				selfProtectionPolicy.setExpectedHeartbeatRate(selfProtectionPolicy.getExpectedHeartbeatRate()+2);
				selfProtectionPolicy.setExpectedHeartbeatThreshold((long)(selfProtectionPolicy.getExpectedHeartbeatRate()*0.85));
			}
			
			registerResponse.setStatus(RegisterResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			registerResponse.setStatus(RegisterResponse.FAILTURE);
		}
		
		return registerResponse;
	}
	
	
	

	
	
	/**
	 * 发送心跳
	 * @param heartbeatRequest
	 * @return
	 */
	public HeartbeatResponse heartbeat(HeartbeatRequest heartbeatRequest) {
		HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
		try {
			ServiceInstance serviceInstance = 
					registry.getServiceInstance(heartbeatRequest.getServiceName(), heartbeatRequest.getServiceInstanceId());
			//
			serviceInstance.renew();
			
			//记录每分钟心跳次数
			HeartbeatMessuredRate heartbeatMessuredRate = HeartbeatMessuredRate.getInstance();
			heartbeatMessuredRate.increment();
			
			heartbeatResponse.setStatus(HeartbeatResponse.SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			heartbeatResponse.setStatus(heartbeatResponse.FAILTURE);
		}
			
		return heartbeatResponse;
	}
	
	
	
	/**
	 * 拉取注册表
	 * @return
	 */
	public Map<String, Map<String,ServiceInstance>> fetchServiceRegistry(){
		return registry.getRegistry();
	}
	
	
	/**
	 * 服务下线
	 */
	public void cancel(String serviceName  , String serviceInstanceId){
		registry.remove(serviceName, serviceInstanceId);
		
		synchronized (SelfProtectionPolicy.class) {
			SelfProtectionPolicy selfProtectionPolicy = SelfProtectionPolicy.getInstance();
			selfProtectionPolicy.setExpectedHeartbeatRate(selfProtectionPolicy.getExpectedHeartbeatRate()  -  2);
			selfProtectionPolicy.setExpectedHeartbeatThreshold((long)(selfProtectionPolicy.getExpectedHeartbeatRate()*0.85));
		}
	}
	
}
