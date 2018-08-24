package com.demo.springcloud.client.isolation;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

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
 * @author yuezh2   2018年8月24日 下午10:30:17
 *
 */
public class CacheMain {
	
	
	/**
	 * 缓存要在一次请求的情况下才能生效 , 需要初始化  上下文 ,
	 * @param args
	 */
	public static void main(String[] args) {
		HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
		String key = "cache-key";
		CacheCommand c1 = new CacheCommand(key);
		CacheCommand c2 = new CacheCommand(key);
		CacheCommand c3 = new CacheCommand(key);
		
		c1.execute();
		c2.execute();
		c3.execute();
		
		System.out.println("c1 是否读取缓存:"+c1.isResponseFromCache());
		System.out.println("c2 是否读取缓存:"+c2.isResponseFromCache());
		System.out.println("c3 是否读取缓存:"+c3.isResponseFromCache());
		
		//清空缓存
		HystrixRequestCache cache = HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("MyCommandKey")
				,HystrixConcurrencyStrategyDefault.getInstance());
		cache.clear(key);
		
		CacheCommand c4 = new CacheCommand(key);
		c4.execute();
		System.out.println("c4 是否读取缓存:"+c4.isResponseFromCache());
		
		ctx.shutdown();
	}
	
	
	
	
	
	static class CacheCommand extends HystrixCommand<String>{
		
		private String cacheKey;
		
		
		
		/**
		 * 
		 */
		protected CacheCommand(String cacheKey ) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TestGroupKey"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("MyCommandKey")));
			this.cacheKey = cacheKey;
		}

		

		/**
		 * 
		 */
		@Override
		protected String run() throws Exception {
			System.out.println("执行方法");
			return "";
		}

		
		/**
		 * 
		 */
		@Override
		protected String getFallback() {
			System.out.println("执行回退");
			return "";
		}

		
		/**
		 * 
		 * @return
		 */
		public String getCacheKey() {
			return cacheKey;
		}
		
		
	}
	
	

}
