package org.demo.netty.team;

import org.demo.netty.domain.TeamSkill;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:45:12
 */
public interface TeamSkillService {
	
	TeamSkill obtainTeamSkill(String tenantCode, String skillCode);
}