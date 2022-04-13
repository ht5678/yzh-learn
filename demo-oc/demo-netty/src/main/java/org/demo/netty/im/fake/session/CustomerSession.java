package org.demo.netty.im.fake.session;


/**
 * 用户session
 * @author yuezh2
 * @date	  2022年3月28日 下午4:50:56
 */
public interface CustomerSession extends Session{
	/**
	 * 获取会话ID
	 * @return
	 */
	String getCid();

	/**
	 * 设置会话ID
	 * @param cid
	 */
	void setCid(String cid);

	/**
	 * 获取租户编码
	 * @return
	 */
	String getTenantCode();

	/**
	 * 获取团队编码
	 * @return
	 */
	String getTeamCode();

	/**
	 * 获取技能编码
	 * @return
	 */
	String getSkillCode();

	/**
	 * 获取技能名称
	 * @return
	 */
	String getSkillName();

	/**
	 * 获取服务客服编码
	 * @return
	 */
	String getWaiterCode();

	/**
	 * 获取服务客服名称
	 * @return
	 */
	String getWaiterName();

	/**
	 * 统一设置服务客服编码和名称
	 * @param waiterCode
	 * @param waiterName
	 */
	void setWaiter(String waiterCode, String waiterName);

	/**
	 * 获取商品编码
	 * @return
	 */
	String getGoodsCode();

	/**
	 * 获取客户信息
	 * @return
	 */
	Customer getCustomer();

	/**
	 * 0 客户未分配客服，进行分配并缓存消息
	 * 1 已经分配客服，发送消息给客服
	 * 2 正在分配客服中，客户等待过程中继续发送的消息进行缓存
	 * @return CustomerAssignStatus
	 */
	CustomerAssignStatus getStatus();

	/**
	 * 0 客户未分配客服，进行分配并缓存消息
	 * 1 已经分配客服，发送消息给客服
	 * 2 正在分配客服中，客户等待过程中继续发送的消息进行缓存
	 * @param status
	 */
	void setStatus(CustomerAssignStatus status);
}