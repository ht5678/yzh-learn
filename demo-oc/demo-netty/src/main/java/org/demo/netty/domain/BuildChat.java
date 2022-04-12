package org.demo.netty.domain;

/**
 * 
 * @author yue
 *
 */
public class BuildChat {

    private String ttc;

    private String tmc;

    private String tmb;

    private String skc;

    private String skn;

    private String gc;

    private boolean login;

    private String device;

    private String reason;

    public BuildChat() {}

    public BuildChat(String ttc, String tmc, String tmb, String skc, String skn,
                     String gc, boolean login, String device) {
        this.ttc = ttc;
        this.tmc = tmc;
        this.tmb = tmb;
        this.skc = skc;
        this.skn = skn;
        this.gc = gc;
        this.login = login;
        this.device = device;
    }

    public BuildChat(String ttc, String tmc, String tmb, String skc, String skn,
                     String gc, boolean login, String device, String reason) {
        this(ttc, tmc, tmb, skc, skn, gc, login, device);
        this.reason = reason;
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

    public String getTmb() {
        return tmb;
    }

    public void setTmb(String tmb) {
        this.tmb = tmb;
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

    public String getGc() {
        return gc;
    }

    public void setGc(String gc) {
        this.gc = gc;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BuildChat{" +
                "ttc='" + ttc + '\'' +
                ", tmc='" + tmc + '\'' +
                ", tmb='" + tmb + '\'' +
                ", skc='" + skc + '\'' +
                ", skn='" + skn + '\'' +
                ", gc='" + gc + '\'' +
                ", login=" + login +
                ", device='" + device + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
