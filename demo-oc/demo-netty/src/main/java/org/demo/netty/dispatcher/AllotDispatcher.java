package org.demo.netty.dispatcher;

import java.util.Collection;
import java.util.List;

import org.demo.netty.dispatcher.register.EventRegister;
import org.demo.netty.dispatcher.room.CustomerRoom;
import org.demo.netty.dispatcher.room.CustomerRoomImpl;
import org.demo.netty.dispatcher.room.WaiterRoom;
import org.demo.netty.dispatcher.room.WaiterRoomImpl;
import org.demo.netty.dispatcher.scheduler.EventScheduler;
import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.BuildChat;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.Team;
import org.demo.netty.domain.Waiter;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.provider.redis.IDProvider;
import org.demo.netty.provider.redis.NearServiceProvider;
import org.demo.netty.register.Event;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerAssignStatus;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.NameFactory;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.store.local.LocalTeamStore;
import org.demo.netty.transfer.TransferTeam;
import org.demo.netty.transfer.TransferWaiter;
import org.demo.netty.util.JsonUtils;
import org.demo.netty.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 调度中心
 * @author yuezh2
 * @date	  2022年4月11日 下午4:32:04
 */
public class AllotDispatcher implements Dispatcher{

	private static Logger log = LoggerFactory.getLogger(AllotDispatcher.class);
	
	
	private NameFactory nameFactory;
	private EventRegister eventRegister;
	private WaiterRoom waiterRoom;
	private CustomerRoom customerTable;
	private EventScheduler eventScheduler;
	
	
	
	/**
	 * 
	 */
	public AllotDispatcher() {
		nameFactory = new NameFactory();
		eventRegister = new EventRegister(nameFactory);
		waiterRoom = new WaiterRoomImpl(nameFactory, eventRegister);
		customerTable = new CustomerRoomImpl(nameFactory);
		eventScheduler = new EventScheduler(this, eventRegister);
		eventScheduler.start();
	}
	
	
	
	
	@Override
	public void login(Waiter waiter) {
		waiterRoom.login(waiter);
	}

	@Override
	public void logout(WaiterSession session) {
		if(null != session) {
			waiterRoom.logout(session.getWaiter().getTeamCode(), session.getUid());
		}
	}

	@Override
	public boolean changeWaiterStatus(String teamCode, String waiterCode, String status) {
		return waiterRoom.changeStatus(teamCode, waiterCode, status);
	}

	@Override
	public Collection<Waiter> getWaiters(String teamCode) {
		return waiterRoom.getWaiters(teamCode);
	}

	@Override
	public Waiter acquireWaiter(String teamCode) {
		return waiterRoom.acquire(teamCode);
	}

	@Override
	public Waiter acquireWaiter(String teamCode, String waiterCode) {
		return waiterRoom.acquire(teamCode , waiterCode);
	}

	@Override
	public Customer acquireCustomer(String teamCode) {
		return customerTable.acquire(teamCode);
	}
	
	/**
	 * 
	 */
	@Override
	public int addQueueWait(Customer customer) {
		customer.setTime(System.currentTimeMillis());
		int waitNo = customerTable.addWait(customer);
		Body body = new Body(BodyType.WAITING, String.valueOf(waitNo));
		AddressTo to = new AddressTo(customer.getUid(),  Identity.CUSTOMER);
		AddressFrom from = new AddressFrom("sys",Identity.SYS);
		Packet packet = new Packet(PacketType.BUILD_CHAT, from, to, body);
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
		return waitNo;
	}

	/**
	 * 
	 */
	@Override
	public List<Customer> getQueueWaits(String teamCode) {
		return customerTable.getWaits(teamCode);
	}
	
	/**
	 * 
	 */
	@Override
	public void removeQueueWait(Customer customer) {
		customerTable.removeWait(customer);
	}

	@Override
	public boolean existQueueWait(String teamCode) {
		return 0 == customerTable.size(teamCode)?false:true;
	}
	
	/**
	 * 
	 */
	@Override
	public void registerAllotEvent(Event event) {
		if(!hasAllotEvent(event)){
			eventRegister.register(event);
		}else{
			log.info("分配事件已经存在 : {}" , event);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public boolean hasAllotEvent(Event event) {
		return eventRegister.hasEvent(event);
	}

	@Override
	public boolean cancelAllotRegister(Event event) {
		return eventRegister.cancel(event);
	}
	
	/**
	 * 
	 */
	@Override
	public void buildRelation(Event event) {
		if(null!=event){
			switch (event.getType()) {
			case CUSTOMER_REQ:
				dealcustomer
				break;

			default:
				break;
			}
		}
	}
	
	
	/**
	 * 客户发起请求
	 */
	private void dealCustomerRequestEvent(Event event) {
		CustomerSession customerSession = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(event.getUid());
		if(null!=customerSession) {
			allot
		}
	}
	
	
	/**
	 * 分配客服
	 * @return
	 */
	private Waiter allotWaiterForCustomer(CustomerSession customerSession) {
		Customer customer = customerSession.getCustomer();
		//判断排队队列是否有排队用户 , 如果存在直接进行排队
		if(isWaiting(customer)){
			return null;
		}
		//
		synchronized (customerSession) {
			if(customerSession.getStatus() != CustomerAssignStatus.ASSIGNED){
				//记忆分配
				Waiter waiter = memoryAllot(customer);
				//默认按照轮训分配客服
				if(null == waiter){
					waiter = cyclicAllot(customer);
				}
				//分配客服
				if(null != waiter){
					customerSession.setWaiter(waiter.getWaiterCode(), waiter.getWaiterName());
					build
				}
			}
		}
	}
	
	
	/**
	 * 判断是否排队
	 * @return
	 */
	private boolean isWaiting(Customer customer) {
		//判断排队队列是否有排队客户, 如果存在直接进行排队
		if(this.existQueueWait(customer.getTeamCode())) {
			this.addQueueWait(customer);
			log.info("团队编号: {}  , 当前团队客服繁忙进入排队队列 , 当前客户: {}" , customer.getTeamCode() , customer.getUid());
			return true;
		}

		return false;
	}
	
	
	/**
	 * 记忆分配
	 * @return
	 */
	private Waiter memoryAllot(Customer customer) {
		Waiter waiter = null;
		String assignRule = LocalTeamStore.getInst().getTeamAssignRule(customer.getTeamCode());
		if("0".equals(assignRule)){
			String nearWaiterCode = NearServiceProvider.getInst().getNearWaiter(customer.getTeamCode(), customer.getUid());
			if(null != nearWaiterCode){
				waiter = this.acquireWaiter(customer.getTeamCode() , nearWaiterCode);
			}
			//
			if(null != waiter){
				log.info("团队编号：{}, 采用记忆分配规则. 当前客户：{}", customer.getTeamCode(), customer.getUid());
			}
		}
		return waiter;
	}
	
	
	/**
	 * 轮训分配
	 * @return
	 */
	private Waiter cyclicAllot(Customer customer) {
		Waiter waiter = this.acquireWaiter(customer.getTeamCode());
		//当前客服繁忙 , 没有空闲客服 , 客户加入等待队列
		if(null == waiter){
			this.addQueueWait(customer);
			log.info("团队编号：{}, 未获取到客服资源，加入等待队列. 当前客户：{}", customer.getTeamCode(), customer.getUid());
		} else {
			log.info("团队编号：{}, 采用轮训分配规则. 当前客户：{}", customer.getTeamCode(), customer.getUid());
		}
		return waiter;
	}
	
	
	
	/**
	 * 构建会话
	 */
	private void buildChat(CustomerSession customerSession , Waiter waiter) {
		//设置客户分配状态为已分配
		customerSession.setStatus(CustomerAssignStatus.ASSIGNED);
		//生成chat id
		String chatId = IDProvider.getInstance().getChatId();
		customerSession.setCid(chatId);
		//分配客服成功, 给用户发消息
		notice
	}
	
	
	/**
	 * 通知客户分配客服成功 , 构建会话成功
	 */
	private void noticeBuildChatToCustomer(CustomerSession customerSession) {
		String uid = customerSession.getUid();
		String waiterCode = customerSession.getWaiterCode();
		Packet buildChatPacket = new Packet(PacketType.BUILD_CHAT);
		
		String content = createBuildChat(customerSession);
		Body body = new Body(BodyType.SUCCESS, content);
		AddressTo to = new AddressTo(uid, customerSession.getIdy());
		AddressFrom from = new AddressFrom(waiterCode, Identity.SYS);
		buildChatPacket.setPid(UUIDUtils.createPid(901));
		
	}
	
	
	
	/**
	 * 构建会话信息
	 * @return
	 */
	private String createBuildChat(CustomerSession customerSession) {
		Customer customer = customerSession.getCustomer();
		Team team = LocalTeamStore.getInst().getTeam(customer.getTeamCode());
		BuildChat buildChat = new BuildChat(customer.getTenantCode(), customer.getTeamCode(), team.getBriefName(), customer.getSkillCode()
				, customer.getSkillName(), customer.getGoodsCode(), customer.isLogin(), customer.getDevice());
		String content = null;
		try {
			content = JsonUtils.getJson().writeString(buildChat);
		} catch (Exception e) {
			log.error("序列化失败 BuildChat: {}", buildChat);
			e.printStackTrace();
		}
		return content;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void directReleaseRelation(String waiterCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean transferByWaiter(TransferWaiter transferWaiter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transferByTeam(TransferTeam transferTeam) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeChat(CustomerSession session) {
		// TODO Auto-generated method stub
		
	}

	
	
}
