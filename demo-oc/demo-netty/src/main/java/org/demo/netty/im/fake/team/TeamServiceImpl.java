package org.demo.netty.im.fake.team;

import java.util.ArrayList;
import java.util.List;

import org.demo.netty.im.fake.domain.Team;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:35:52
 */
public class TeamServiceImpl implements TeamService{

//	@Autowired
//	private TeamMapper teamMapper;
	
	@Override
	public List<Team> obtainTeams(String tenantCode) {
//		return teamMapper.obtainTeams(tenantCode);
		return new ArrayList<Team>();
	}

	@Override
	public Team obtainTeam(String teamCode) {
//		return teamMapper.obtainTeam(teamCode);
		return new Team();
	}

}