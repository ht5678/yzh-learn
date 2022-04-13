package org.demo.netty.im.fake.team;

import org.demo.netty.im.fake.domain.TeamSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:47:32
 */
@Service
public class TeamSkillServiceImpl implements TeamSkillService{

//	@Autowired
//	private TeamSkillMapper teamSkillMapper;
	
	@Override
	public TeamSkill obtainTeamSkill(String tenantCode, String skillCode) {
//		return teamSkillMapper.obtainTeamSkill(tenantCode, skillCode);
		return new TeamSkill();
	}
}