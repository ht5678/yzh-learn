package com.demo.springcloud.feign.contract;




public interface ContractClient {

	@MyUrl(url="/hello",method="GET")
	public String hello();
	
	
}
