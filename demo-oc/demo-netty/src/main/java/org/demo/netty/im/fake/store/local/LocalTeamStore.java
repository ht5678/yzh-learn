package org.demo.netty.im.fake.store.local;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.demo.netty.im.fake.domain.SkillBusiness;
import org.demo.netty.im.fake.domain.Team;
import org.demo.netty.im.fake.domain.TeamSkill;
import org.demo.netty.im.fake.skill.SkillBusinessService;
import org.demo.netty.im.fake.skill.SkillBusinessServiceImpl;
import org.demo.netty.im.fake.team.TeamService;
import org.demo.netty.im.fake.team.TeamServiceImpl;
import org.demo.netty.im.fake.team.TeamSkillService;
import org.demo.netty.im.fake.team.TeamSkillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 团队功能数据缓存
 * @author yuezh2
 * @date	  2022年3月28日 下午10:33:12
 */
public class LocalTeamStore {
	
	private static Logger log = LoggerFactory.getLogger(LocalTeamStore.class);
	
	//缓存过期时间 秒
	private static long EXPIRE_OF_SECONDS =  60 * 5;
	
	
//	private TeamService teamService = SpringContext.getBean(TeamServiceImpl.class);
	private TeamService teamService = new TeamServiceImpl();
	
//	private TeamSkillService teamSkillService = SpringContext.getBean(TeamSkillServiceImpl.class);
	private TeamSkillService teamSkillService = new TeamSkillServiceImpl();
	
//	private SkillBusinessService skillBusinessService = SpringContext.getBean(SkillBusinessServiceImpl.class);
	private SkillBusinessService skillBusinessService = new SkillBusinessServiceImpl();
	
	
	private LoadingCache<String, Team> teamCache;
	private LoadingCache<TeamSkillSearchCondition, TeamSkill> teamSkillCache;
	private LoadingCache<String, SkillBusiness> skillBusinessCache;
	
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	
	/**
	 * 
	 */
	private LocalTeamStore() {
		initCache();
	}
	
	
	/**
	 * 
	 */
	private void initCache() {
		//
		teamCache = CacheBuilder.newBuilder()
					.expireAfterWrite(EXPIRE_OF_SECONDS, TimeUnit.SECONDS)
					.build(new CacheLoader<String, Team>(){

						@Override
						public Team load(String key) throws Exception {
							return teamService.obtainTeam(key);
						}
						
					});
		
		//
		teamSkillCache = CacheBuilder.newBuilder()
						.expireAfterWrite(EXPIRE_OF_SECONDS, TimeUnit.SECONDS)
						.build(new CacheLoader<TeamSkillSearchCondition, TeamSkill>(){

							@Override
							public TeamSkill load(TeamSkillSearchCondition condition) throws Exception {
								return teamSkillService.obtainTeamSkill(condition.getTenantCode(), condition.getSkillCode());
							}
							
						});
		
		
		//
		skillBusinessCache = CacheBuilder.newBuilder()
					.expireAfterWrite(EXPIRE_OF_SECONDS, TimeUnit.SECONDS)
					.build(new CacheLoader<String, SkillBusiness>(){

						@Override
						public SkillBusiness load(String key) throws Exception {
							String[] s = key.split(":");
							return skillBusinessService.obtainSkillBusiness(s[0], s[1]);
						}
						
					});
		
	}
	
	
	/**
	 * 根据团队编码获取团队信息
	 * @return
	 */
	public Team getTeam(String teamCode) {
		try {
			return teamCache.get(teamCode);
		}catch(Exception e) {
			log.error("根据团队编码:{}  , 获取团队信息发生异常 : {}" , teamCode , e);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getSkillName(String tenantCode , String skillCode) {
		TeamSkillSearchCondition condition = new TeamSkillSearchCondition(tenantCode, skillCode);
		try {
			TeamSkill teamSkill = teamSkillCache.get(condition);
			if(null != teamSkill) {
				return teamSkill.getSkillName();
			}
		}catch(ExecutionException e) {
			log.error("获取团队技能为空condition: {}",condition);
		}
		return null;
	}
	
	
	
	/**
	 * 分配规则 0 记忆分配 1 轮训分配 2 空闲分配
	 * 如果没有配置则默认为 : 记忆分配
	 * @return
	 */
	public String getTeamAssignRule(String teamCode) {
		String result = "0";
		try {
			Team team = teamCache.get(teamCode);
			if(null != team) {
				result = StringUtils.isEmpty(team.getAssignRule()) ? "0" : team.getAssignRule();
			}
		}catch(ExecutionException e) {
			log.error("根据团队编码: {} , 获取团队信息发生异常 : {}" , teamCode , e);
		}
		return result;
	}
	
	
	
	/**
	 * 根据团队编码获取当前团队自动回复语
	 * 如果开启自动回复 , 则返回自动回复语
	 * @return
	 */
	public String getTeamReply(String teamCode) {
		String replyMsg = null;
		try {
			Team team = teamCache.get(teamCode);
			if(null != team && "1".equals(team.getAutoReply())) {
				replyMsg = team.getReplyMsg();
			}
		}catch(ExecutionException e) {
			log.error("根据团队编码 : {} , 获取自动回复语发生异常: {}" , teamCode , e);
		}
		return replyMsg;
	}
	
	
	
	/**
	 * 获取团队当前是否处于工作状态
	 * @return
	 */
	public Result getTeamServiceState(String teamCode) {
		Result result = null;
		try {
			Team team = teamCache.get(teamCode);
			long beginTime = getTime(team.getBeginTime());
			long endTime = getTime(team.getEndTime());
			long currTime = getTime(format.format(new Date()));
			//开始时间 < 结束时间 , 表示未跨天服务 (0-24)
			//开始时间 < 结束时间 , 表示已经跨天服务(0-0)
			if(beginTime < endTime) {
				if(currTime > beginTime && currTime < endTime) {
					result = new Result(true);
				}
			}else {
				if (currTime < beginTime && currTime < endTime) {
					result = new Result(true);
				}

				if (currTime > beginTime) {
					result = new Result(true);
				}
			}
			
			//如果result为null , 说明当前团队在工作时间 , 可以接待客户
			if(null == result) {
				String offlineMessage = team.getOfflineMessage();
				offlineMessage = offlineMessage.replaceAll("\\{begin_time\\}", team.getBeginTime());
				offlineMessage = offlineMessage.replaceAll("\\{end_time\\}", team.getEndTime());
				result = new Result(false, offlineMessage);
			}
		}catch(ExecutionException e) {
			log.error("计算服务时间发生异常: {}" , e);
			result = new Result(false, "聊天服务器处于繁忙状态 , 请您稍后再资讯!");
		}
		return result;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static LocalTeamStore getInst() {
		return Single.instance;
	}
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年3月29日 下午3:25:48
	 */
	private static class Single {
		private static LocalTeamStore instance = new LocalTeamStore();
	}
	
	
	
	/**
	 * 字符串时间 转 - 秒
	 * @return
	 */
	private long getTime(String timeStr) {
		String[] split = timeStr.split("\\:");
		return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
	}
	
	
	
	/**
	 * 返回结果
	 * @author yuezh2
	 * @date	  2022年3月29日 下午3:16:37
	 */
	public static class Result {
		private boolean online;
		private String message;
		
		/**
		 * 
		 */
		public Result(boolean online) {
			this(online , null);
		}
		
		/**
		 * 
		 * @param online
		 * @param message
		 */
		public Result(boolean online , String message) {
			this.online = online;
			this.message = message;
		}
		
		
		

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
		
	}
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年3月28日 下午10:55:04
	 */
	private static class TeamSkillSearchCondition {

		private String tenantCode;

		private String skillCode;

		public TeamSkillSearchCondition(String tenantCode, String skillCode) {
			this.tenantCode = tenantCode;
			this.skillCode = skillCode;
		}

		public String getTenantCode() {
			return tenantCode;
		}

		public void setTenantCode(String tenantCode) {
			this.tenantCode = tenantCode;
		}

		public String getSkillCode() {
			return skillCode;
		}

		public void setSkillCode(String skillCode) {
			this.skillCode = skillCode;
		}

		@Override
		public String toString() {
			return "TeamSkillSearchCondition{" +
					"tenantCode='" + tenantCode + '\'' +
					", skillCode='" + skillCode + '\'' +
					'}';
		}
	}

}



