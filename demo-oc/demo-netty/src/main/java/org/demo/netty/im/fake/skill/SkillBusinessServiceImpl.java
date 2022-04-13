package org.demo.netty.im.fake.skill;

import org.demo.netty.im.fake.domain.SkillBusiness;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:51:03
 */
@Service
public class SkillBusinessServiceImpl implements SkillBusinessService{

//	@Autowired
//	private SkillBusinessMapper skillBusinessMapper;
	
	/**
	 * @param tenantCode
	 * @param businessCode
	 * @return
	 */
	@Override
	public SkillBusiness obtainSkillBusiness(String tenantCode, String businessCode) {
//		return skillBusinessMapper.obtainSkillBusiness(tenantCode, businessCode);
		return new SkillBusiness();
	}

}