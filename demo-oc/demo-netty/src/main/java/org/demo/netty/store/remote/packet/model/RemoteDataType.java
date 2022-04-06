package org.demo.netty.store.remote.packet.model;



/**
 * 存储消息类型
 * @author yuezh2
 */
public enum RemoteDataType {
    /**
     * 正常消息
     */
    NORMAL,
    /**
     * 离线消息
     */
    OFFLINE,
    /**
     * 撤回消息
     */
    REVOCATION;
}