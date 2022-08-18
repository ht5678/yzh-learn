package org.demo.im.sdk.android;


/**
 * 
 * @author yue
 *
 */
public class TestClient {

	public static void main(String[] args) throws Exception{
		//在这里应该是IM系统的ip list服务 , 随机获取一台机器的ip
		ImClient imClient = new ImClient();
		imClient.connect("127.0.0.1", 8080);
		imClient.send("test002", "test002_token");
		
		while(true){
			Thread.sleep(1000);
		}
	}
	
}
