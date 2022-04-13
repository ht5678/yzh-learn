package org.demo.netty.im.fake.skill;

import org.demo.netty.im.fake.domain.SkillBusiness;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:49:07
 */
public interface SkillBusinessService {

	SkillBusiness obtainSkillBusiness(String tenantCode, String businessCode);
	
}