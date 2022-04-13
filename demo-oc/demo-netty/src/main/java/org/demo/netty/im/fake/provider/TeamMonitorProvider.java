package org.demo.netty.im.fake.provider;

/**
 * 团队监控
 * @author yuezh2
 */
public class TeamMonitorProvider {

//    private TeamMonitorService teamMonitorService = SpringContext.getBean(TeamMonitorServiceImpl.class);

    private TeamMonitorProvider() {}

    public int updateWaitCount(String tenantCode, String teamCode, Integer waitCount) {
//        return teamMonitorService.updateWaitCount(tenantCode, teamCode, waitCount);
    	return 0;
    }

    public static TeamMonitorProvider getInst() {
        return Single.inst;
    }

    private static class Single {
        private static TeamMonitorProvider inst = new TeamMonitorProvider();
    }
}
