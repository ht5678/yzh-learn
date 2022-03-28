package org.demo.netty.domain;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午10:36:39
 */
public class Team {

	private String tenantCode;
	
	private String teamName;
	
	private String briefName;
	
	private String teamCode;
	
	private String needLogin;
	
	private String loginUrl;
	
	private String assignRule;
	
	private String beginTime;
	
	private String endTime;
	
	private String offlineMessage;
	
	private String autoReply;
	
	private String replyMsg;
	
	private String flag;
	
	private String createTime;
	
	


	
	
    public String getTenantCode() {
        return tenantCode;
    }

	public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode == null ? null : tenantCode.trim();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

	public String getBriefName() {
		return briefName;
	}

	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode == null ? null : teamCode.trim();
    }
    
    public String getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(String needLogin) {
		this.needLogin = needLogin;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getAssignRule() {
		return assignRule;
	}

	public void setAssignRule(String assignRule) {
		this.assignRule = assignRule;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOfflineMessage() {
		return offlineMessage;
	}

	public void setOfflineMessage(String offlineMessage) {
		this.offlineMessage = offlineMessage;
	}
	
	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
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
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "Team{" +
				"tenantCode='" + tenantCode + '\'' +
				", teamName='" + teamName + '\'' +
				", briefName='" + briefName + '\'' +
				", teamCode='" + teamCode + '\'' +
				", needLogin='" + needLogin + '\'' +
				", loginUrl='" + loginUrl + '\'' +
				", assignRule='" + assignRule + '\'' +
				", beginTime='" + beginTime + '\'' +
				", endTime='" + endTime + '\'' +
				", offlineMessage='" + offlineMessage + '\'' +
				", autoReply='" + autoReply + '\'' +
				", replyMsg='" + replyMsg + '\'' +
				", flag='" + flag + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}
