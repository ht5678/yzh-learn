package org.demo.netty.im.fake.im.auth;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午5:08:13
 */
public class CustomerInfo {

    /**
     * 访客名称
     */
    private String cn;
    /**
     * lenovoId
     */
    private String cc;
    /**
     * 租户编码
     */
    private String ttc;
    /**
     * 团队
     */
    private String tmc;
    /**
     * 技能
     */
    private String skc;
    /**
     * 技能
     */
    private String skn;
    /**
     * 业务编码
     */
    private String buc;
    /**
     * 设备
     * 1 pc
     * 2 wap
     * 3 app
     * 4 web chat
     * 5 其他
     */
    private String device;

    private String gc;
    /**
     * 是否登录（0:未登录，1:登录）
     */
    private String real;

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTtc() {
        return ttc;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }

    public String getTmc() {
        return tmc;
    }

    public void setTmc(String tmc) {
        this.tmc = tmc;
    }

    public String getSkc() {
        return skc;
    }

    public void setSkc(String skc) {
        this.skc = skc;
    }

    public String getSkn() {
        return skn;
    }

    public void setSkn(String skn) {
        this.skn = skn;
    }

    public String getBuc() {
        return buc;
    }

    public void setBuc(String buc) {
        this.buc = buc;
    }

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerName='" + cn + '\'' +
                ", customerCode='" + cc + '\'' +
                ", tenantCode='" + ttc + '\'' +
                ", teamCode='" + tmc + '\'' +
                ", skillCode='" + skc + '\'' +
                ", bussinessCode='" + buc + '\'' +
                ", device='" + device + '\'' +
                ", real='" + real + '\'' +
                '}';
    }
}
