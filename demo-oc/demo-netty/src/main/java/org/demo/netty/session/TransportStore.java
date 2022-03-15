package org.demo.netty.session;

import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Transport;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午5:06:58
 */
public interface TransportStore {

    /**
     * 获取当前消息
     * @return
     */
    CustomQueue<Packet> getPacketsQueue();

    /**
     * 获取缓存消息
     * @return
     */
    CustomQueue<Packet> getFuturePackets();

    /**
     * 获取传输类型
     * @return
     */
    Transport getTransport();
}