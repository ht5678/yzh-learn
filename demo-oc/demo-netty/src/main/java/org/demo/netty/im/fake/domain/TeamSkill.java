package org.demo.netty.im.fake.domain;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:45:45
 */
public class TeamSkill {

	private String tenantCode;
	
	private String teamCode;
	
	private String skillCode;
	
	private String skillName;
	
	private String flag;
	
	private String createTime;
	
	
	
	   public String getTenantCode() {
	        return tenantCode;
	    }

	    public void setTenantCode(String tenantCode) {
	        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
	    }

	    public String getTeamCode() {
	        return teamCode;
	    }

	    public void setTeamCode(String teamCode) {
	        this.teamCode = teamCode == null ? null : teamCode.trim();
	    }

	    public String getSkillCode() {
	        return skillCode;
	    }

	    public void setSkillCode(String skillCode) {
	        this.skillCode = skillCode == null ? null : skillCode.trim();
	    }

	    public String getSkillName() {
	        return skillName;
	    }

	    public void setSkillName(String skillName) {
	        this.skillName = skillName == null ? null : skillName.trim();
	    }

	    public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(String createTime) {
	    	this.createTime = createTime == null ? null : createTime.trim();
	    }

	    @Override
	    public String toString() {
	        return "TeamSkill{" +
	                "tenantCode='" + tenantCode + '\'' +
	                ", teamCode='" + teamCode + '\'' +
	                ", skillCode='" + skillCode + '\'' +
	                ", skillName='" + skillName + '\'' +
	                ", flag='" + flag + '\'' +
	                ", createTime='" + createTime + '\'' +
	                '}';
	    }
	}
