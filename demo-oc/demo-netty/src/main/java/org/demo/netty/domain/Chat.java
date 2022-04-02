package org.demo.netty.domain;

import java.util.Date;


/**
 * 
 */
public class Chat {
    private Long id;

    private String chatId;

    private String tenantCode;
    
    private String teamCode;

    private String skillCode;
    
    private String goodsCode;
    
    private String customerName;

    private String customerCode;

    private String waiterName;

    private String waiterCode;

    private String isLogin;

    private String isTransfer;

    private String type;

    private String isEffective;

    private Integer waitingTime;

    private Integer msgTotal;

    private Integer waiterMsgTotal;

    private Integer customerMsgTotal;

    private String opinion;

    private String suggest;

    private String deviceType;
    
    private String closeType;

    private String createTime;

    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }

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
		this.teamCode = teamCode;
	}

	public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode == null ? null : skillCode.trim();
    }
    
    public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName == null ? null : waiterName.trim();
    }

    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode == null ? null : waiterCode.trim();
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin == null ? null : isLogin.trim();
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer == null ? null : isTransfer.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective == null ? null : isEffective.trim();
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Integer getMsgTotal() {
        return msgTotal;
    }

    public void setMsgTotal(Integer msgTotal) {
        this.msgTotal = msgTotal;
    }

    public Integer getWaiterMsgTotal() {
        return waiterMsgTotal;
    }

    public void setWaiterMsgTotal(Integer waiterMsgTotal) {
        this.waiterMsgTotal = waiterMsgTotal;
    }

    public Integer getCustomerMsgTotal() {
        return customerMsgTotal;
    }

    public void setCustomerMsgTotal(Integer customerMsgTotal) {
        this.customerMsgTotal = customerMsgTotal;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest == null ? null : suggest.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType == null ? null : closeType.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	@Override
	public String toString() {
		return "Chat [id=" + id + ", chatId=" + chatId + ", tenantCode=" + tenantCode + ", teamCode=" + teamCode
				+ ", skillCode=" + skillCode + ", goodsCode=" + goodsCode + ", customerName=" + customerName
				+ ", customerCode=" + customerCode + ", waiterName=" + waiterName + ", waiterCode=" + waiterCode
				+ ", isLogin=" + isLogin + ", isTransfer=" + isTransfer + ", type=" + type + ", isEffective="
				+ isEffective + ", waitingTime=" + waitingTime + ", msgTotal=" + msgTotal + ", waiterMsgTotal="
				+ waiterMsgTotal + ", customerMsgTotal=" + customerMsgTotal + ", opinion=" + opinion + ", suggest="
				+ suggest + ", deviceType=" + deviceType + ", closeType=" + closeType + ", createTime=" + createTime
				+ ", endTime=" + endTime + "]";
	}
}