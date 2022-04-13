package org.demo.netty.store.local;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.demo.netty.domain.Waiter;
import org.demo.netty.provider.context.SpringContext;
import org.demo.netty.service.WaiterService;
import org.demo.netty.service.WaiterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 客服信息本地缓存
 * @author yuezh2
 * @date	  2022年4月13日 下午4:22:28
 */
public class LocalWaiterStore {
	private static Logger log = LoggerFactory.getLogger(LocalWaiterStore.class);
	private static long expireOfSeconds = 60 * 5;
	private static String autoReply = "1";
	
//	private WaiterService waiterService = SpringContext.getBean(WaiterServiceImpl.class);
	
	private WaiterService waiterService = null;
	
	private LoadingCache<String, Waiter> waiterByCodeCache;

	private LocalWaiterStore() {
		initCache();
	}

	/**
	 * 初始化本地缓存
	 */
	private void initCache() {
		waiterByCodeCache = CacheBuilder.newBuilder()
				.expireAfterWrite(expireOfSeconds, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Waiter>() {

					@Override
					public Waiter load(String waiterCode) throws Exception {
						return waiterService.obtainWaiterByCode(waiterCode);
					}
				});
	}
	
	/**
	 * 根据客服编码获取客服所在团队
	 * @param waiterCode
	 * @return
	 */
	public String getTeamCode(String waiterCode) {
		try {
			Waiter waiter = waiterByCodeCache.get(waiterCode);
			if (null != waiter) {
				return waiter.getTeamCode();
			}
		} catch (ExecutionException e) {
			log.error("根据编码：{}, 获取客服团队编码发生异常：{}", waiterCode, e);
		}
		return null;
	}
	
	/**
	 * 根据客服编码获取客服自动回复语
	 * 如果客服设置自动回复，则返回消息内容。
	 * 否则返回NULL
	 * @param waiterCode
	 * @return
	 */
	public Result getWaiterReply(String waiterCode) {
		try {
			Waiter waiter = waiterByCodeCache.get(waiterCode);
			if (null != waiter && autoReply.equals(waiter.getAutoReply())) {
				return new Result(0, waiter.getReplyMsg());
			}
		} catch (ExecutionException e) {
			log.error("根据编码：{}, 获取客服团队编码发生异常：{}", waiterCode, e);
		}
		return new Result(-1, "未开启自动回复");
	}

	public static class Result {
		private int rc;

		private String msg;

		public Result(int rc) {
			this.rc = rc;
		}

		public Result(int rc, String msg) {
			this.rc = rc;
			this.msg = msg;
		}

		public int getRc() {
			return rc;
		}

		public void setRc(int rc) {
			this.rc = rc;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public static LocalWaiterStore getInst() {
		return Single.instance;
	}
	
	private static class Single {
		private static LocalWaiterStore instance = new LocalWaiterStore();
	}
}
