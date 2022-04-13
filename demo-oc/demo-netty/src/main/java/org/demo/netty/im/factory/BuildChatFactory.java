package org.demo.netty.im.factory;

import java.io.IOException;

import org.demo.netty.domain.BuildChat;
import org.demo.netty.domain.Team;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.store.local.LocalTeamStore;
import org.demo.netty.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月13日 下午5:26:10
 */
public class BuildChatFactory {

    private static Logger log = LoggerFactory.getLogger(BuildChatFactory.class);

    /**
     * 构建会话信息
     * @param customerSession
     * @return
     */
    public static String createBuildChatToJson(CustomerSession customerSession) {
        BuildChat buildChat = createBuildChat(customerSession);
        return buildChattoJson(buildChat);
    }

    /**
     * 构建会话信息
     * @param customerSession
     * @return
     */
    public static String createBuildChatToJson(CustomerSession customerSession, String reason) {
        BuildChat buildChat = createBuildChat(customerSession);
        buildChat.setReason(reason);
        return buildChattoJson(buildChat);
    }

    private static String buildChattoJson(BuildChat buildChat) {
        String content = null;
        try {
            content = JsonUtils.getJson().writeString(buildChat);
        } catch (IOException e) {
            log.error("序列化失败 BuildChat: {}", buildChat);
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 构建会话信息
     * @param customerSession
     * @return
     */
    private static BuildChat createBuildChat(CustomerSession customerSession) {
        Customer customer = customerSession.getCustomer();
        Team team = LocalTeamStore.getInst().getTeam(customer.getTeamCode());
        BuildChat buildChat = new BuildChat(customer.getTenantCode(), customer.getTeamCode(),
                team.getBriefName(),customer.getSkillCode(), customer.getSkillName(),
                customer.getGoodsCode(), customer.isLogin(), customer.getDevice());
        return buildChat;
    }
}
