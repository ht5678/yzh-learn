package org.demo.netty.im.fake.team;

import java.util.List;

import org.demo.netty.im.fake.domain.Team;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:35:17
 */
public interface TeamService {
	
	List<Team> obtainTeams(String tenantCode);
	
	Team obtainTeam(String teamCode);

}