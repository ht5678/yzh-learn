package com.demo.springcloud.client.config;

import rx.Observable;
import rx.Observer;

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
 * @author yuezh2   2018年8月23日 下午8:08:21
 *
 */
public class NormalMain {

	
	public static void main(String[] args) throws Exception{
		HelloCommand command = new HelloCommand("execute");
		String result = command.execute();
		System.out.println(result);
		
		
		command = new HelloCommand("observe");
		Observable<String> ob = command.observe();
		System.out.println(ob.toString());
		
		
		command = new HelloCommand("toObservable");
		ob = command.toObservable();
		ob.subscribe(new Observer() {

			@Override
			public void onCompleted() {
				
			}

			@Override
			public void onError(Throwable arg0) {
				
			}

			@Override
			public void onNext(Object arg0) {
				System.out.println("toObservable:"+arg0);
			}
		});
		
		
		Thread.sleep(1000);
		
	}
	
	
}
