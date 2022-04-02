package org.demo.netty.domain;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:13:20
 */
public class ChatRecord {
    private Long id;
    
    private String tenantCode;
    
    private String teamCode;
    
    private String chatId;
    
    private String messageId;

    private String ownerType;

    private String waiterCode;

    private String waiterName;

    private String customerCode;
    
    private String customerName;

    private String messageType;
    
    private String offline;

    private Integer revocation;
    
    private String messages;
    
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }
    
    public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType == null ? null : ownerType.trim();
    }

    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode == null ? null : waiterCode.trim();
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName == null ? null : waiterName.trim();;
    }

    public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }
    
    public String getOffline() {
		return offline;
	}

	public void setOffline(String offline) {
		this.offline = offline;
	}

    public Integer getRevocation() {
        return revocation;
    }

    public void setRevocation(Integer revocation) {
        this.revocation = revocation;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages == null ? null : messages.trim();
    }

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "ChatRecord [id=" + id + ", tenantCode=" + tenantCode + ", teamCode=" + teamCode + ", chatId=" + chatId
				+ ", messageId=" + messageId + ", ownerType=" + ownerType + ", waiterCode=" + waiterCode
				+ ", customerCode=" + customerCode + ", customerName=" + customerName + ", messageType=" + messageType
				+ ", offline=" + offline + ", messages=" + messages + ", createTime=" + createTime + "]";
	}
}