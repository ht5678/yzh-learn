package mongo;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午3:10:11  
 *
 */
@Document(collection = "mongoorder")
public class  MongoOrder implements Serializable {
    public static final String ACTIVE_STATUS_ACTIVATING = "1";
    public static final String ORDER_TYPE_C2C = "7";
    public static final String ORDER_TYPE_CTO = "9";
    public static final String ORDER_TYPE_OTO = "10";


    public static final String ORDER_STATUS_ACTIVATING = "0";
    public static final String ORDER_STATUS_CANCELLED = "1";

    public static final int PAY_STATUS_NO_PAD = 0;
    public static final int PAY_STATUS_PAID = 1;



    private String orderCode;
    private String orderMainCode;
    private String orderStatus;
    private int payStatus;
    private String faid;
    private int faType;
    private String c1lenovoid;
    private String memberCode;
    private String isAbnormal;
    private String creditTotal = "0";
    private String source;
    private String orderAddType;

    private Integer rewardLeDouNum;

    private String clientIP;


    private String wi;
    private String cid;
    private String createTime;
    private String updatetime;
    private String paymentType;
    private String paymentTypeId; 
    private String lenovoId;
    private String customerOrderCode;
    private int shopId;
    private int terminal;
    private Integer usedLeDouNum;
    private int auditStatus;
    private int accountType;
    private String buyerCode;
    private String beePhone;




    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }


    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getBeePhone() {
        return beePhone;
    }

    public void setBeePhone(String beePhone) {
        this.beePhone = beePhone;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public int getFaType() {
        return faType;
    }

    public void setFaType(int faType) {
        this.faType = faType;
    }

    public int getUsedLeDouNum() {
        return usedLeDouNum == null ? 0 : usedLeDouNum;
    }

    public void setUsedLeDouNum(Integer usedLeDouNum) {
        this.usedLeDouNum = usedLeDouNum;
    }


    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public String getCustomerOrderCode() {
        return customerOrderCode;
    }

    public void setCustomerOrderCode(String customerOrderCode) {
        this.customerOrderCode = customerOrderCode;
    }

    public String getLenovoId() {
        return lenovoId;
    }

    public void setLenovoId(String lenovoId) {
        this.lenovoId = lenovoId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getWi() {
        return wi;
    }

    public void setWi(String wi) {
        this.wi = wi;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }


    public void setRewardLeDouNum(Integer rewardLeDouNum) {
        this.rewardLeDouNum = rewardLeDouNum;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public int getRewardLeDouNum() {
        return rewardLeDouNum == null ? 0 : rewardLeDouNum;
    }

    public void setRewardLeDouNum(int rewardLeDouNum) {
        this.rewardLeDouNum = rewardLeDouNum;
    }

    public String getOrderMainCode() {
        return orderMainCode;
    }

    public void setOrderMainCode(String orderMainCode) {
        this.orderMainCode = orderMainCode;
    }


    public String getOrderAddType() {
        return orderAddType;
    }

    public void setOrderAddType(String orderAddType) {
        this.orderAddType = orderAddType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(String creditTotal) {
        this.creditTotal = creditTotal;
    }

    public String getC1lenovoid() {
        return c1lenovoid;
    }

    public void setC1lenovoid(String c1lenovoid) {
        this.c1lenovoid = c1lenovoid;
    }

    public String getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(String isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }


    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid;
    }


    public boolean isPaid() {
        return this.getPayStatus() == PAY_STATUS_PAID;
    }

    public boolean isCancelled() {
        return ORDER_STATUS_CANCELLED.equals(this.getOrderStatus());
    }

    public boolean isActivating() {
        return ORDER_STATUS_ACTIVATING.equals(this.getOrderStatus());
    }


    @Override
    public String toString() {
        return "MongoOrder{" +
                "orderCode='" + orderCode + '\'' +
                ", orderMainCode='" + orderMainCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus=" + payStatus +
                ", faid='" + faid + '\'' +
                ", c1lenovoid='" + c1lenovoid + '\'' +
                ", memberCode='" + memberCode + '\'' +
                ", isAbnormal='" + isAbnormal + '\'' +
                ", creditTotal='" + creditTotal + '\'' +
                ", source='" + source + '\'' +
                ", orderAddType='" + orderAddType + '\'' +
                ", rewardLeDouNum=" + getRewardLeDouNum() +
                '}';
    }
}
