package org.demo.netty.im.fake.domain;

import org.apache.ibatis.javassist.NotFoundException;

/**
 * 客服状态
 * @author pengzq1
 */

public enum WaiterStatus {
    /**
     * 在线
     */
    ONLINE("1"),
    /**
     * 忙碌
     */
    BUSY("2"),
    /**
     * 挂起
     */
    HANG("3"),
    /**
     * 离开
     */
    LEAVE("4");

    private String value;

    private WaiterStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public WaiterStatus getNameByValue(String value) throws NotFoundException {
        WaiterStatus[] values = WaiterStatus.values();
        for(WaiterStatus waiterStatus : values) {
            if (value.equals(waiterStatus.value)) {
                return waiterStatus;
            }
        }
        throw new NotFoundException("未找到与当前值：" + value + "，匹配类型");
    }
}
