package org.demo.netty.store.local;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.demo.netty.domain.SkillBusiness;
import org.demo.netty.domain.Team;
import org.demo.netty.domain.TeamSkill;
import org.demo.netty.skill.SkillBusinessService;
import org.demo.netty.skill.SkillBusinessServiceImpl;
import org.demo.netty.team.TeamService;
import org.demo.netty.team.TeamServiceImpl;
import org.demo.netty.team.TeamSkillService;
import org.demo.netty.team.TeamSkillServiceImpl;
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



