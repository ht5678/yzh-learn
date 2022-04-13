package org.demo.netty.im.fake.boot;

import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.domain.WaiterLog;
import org.demo.netty.im.fake.exception.SaslFailureException;
import org.demo.netty.im.fake.service.WaiterLogServiceImpl;
import org.demo.netty.im.fake.service.WaiterService;
import org.demo.netty.im.fake.service.WaiterServiceImpl;
import org.demo.netty.im.fake.util.PwdEncrypt;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:04:24
 */
public class WaiterProvider {

	private WaiterService waiterService = new WaiterServiceImpl();
	private WaiterLogServiceImpl waiterLogService = new WaiterLogServiceImpl();
	
	
	/**
	 * 客服授权登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public Waiter authentication(String userName , String password) throws SaslFailureException{
		String md5Password = PwdEncrypt.createMD5Password(userName, password);
		
//		Waiter waiter = waiterService.login(userName, md5Password);
		Waiter waiter = new Waiter();
		waiter.setId(1L);
		waiter.setTeamCode("test");
		
		if(null != waiter) {
			return waiter;
		}
		throw new SaslFailureException("鉴权失败", "用户名或密码");
	} 
	
	
	/**
	 * 更新id为当前的客服状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(Long id , String status) {
		return waiterService.updateStatus(id, status);
	}
	
	
	/**
	 * 按客服工号更新当前接待量
	 * @param id
	 * @param reception
	 * @return
	 */
	public int updateCurReception(Long id , Integer reception) {
		return waiterService.updateCurReception(id, reception);
	}
	
	
	/**
	 * 按客服工号更新当前接待量
	 * @param waiterCode 客服工号
	 * @param reception 接待数
	 * @return
	 */
	public int updateCurReceptionByCode(String waiterCode, Integer reception) {
		return waiterService.updateCurReceptionByCode(waiterCode, reception);
	}

	/**
	 * 客服退出登录
	 * @param id
	 * @return
	 */
	public int waiterLogout(Long id) {
		return waiterService.waiterLogout(id);
	}
	
	
	/**
	 * 保存客服登录日志
	 * @param waiter
	 * @param type 1登录 2切换状态 3登出 4异常登出
	 * @param ip
	 * @return
	 */
	public int insertWaiterLog(Waiter waiter, String type, String ip) {
		WaiterLog record = new WaiterLog();
		record.setTenantCode(waiter.getTenantCode());
		record.setTeamCode(waiter.getTeamCode());
		record.setWaiterName(waiter.getWaiterName());
		record.setWaiterCode(waiter.getWaiterCode());
		record.setIp(ip);
		record.setStatus(waiter.getStatus());
		record.setType(type);
		return waiterLogService.insert(record);
	}

	public static WaiterProvider getInst() {
		return Single.instance;
	}
	
	private static class Single {
		private static WaiterProvider instance = new WaiterProvider();
	}
	
}
