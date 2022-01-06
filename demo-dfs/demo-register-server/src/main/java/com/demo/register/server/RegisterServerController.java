package com.demo.register.server;



/**
 * 这个controller负责接收register-client发送过来的请求
 * 在Spring Cloud Eureka中用的组件是jersey  , 
 * 在国外常用jersey , restful框架 , 可以接收http请求
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:31:58
 */
public class RegisterServerController {

	private Registry registry = Registry.getInstance();
	
	
	/**
	 * 服务注册
	 * @param request
	 * @return
	 */
	public RegisterResponse register(RegisterRequest request) {
		RegisterResponse registerResponse = new RegisterResponse();
		try {
			ServiceInstance serviceInstance = new ServiceInstance();
			serviceInstance.setHostname(request.getHostname());
			serviceInstance.setIp(request.getIp());
			serviceInstance.setPort(request.getPort());
			serviceInstance.setServiceInstanceId(request.getServiceInstanceId());
			serviceInstance.setServiceName(request.getServiceName());
			serviceInstance.setLease(new Lease());

			registry.register(serviceInstance);
			
			registerResponse.setStatus(RegisterResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			registerResponse.setStatus(RegisterResponse.FAILTURE);
		}
		
		return registerResponse;
	}
	
	
	
	
}
