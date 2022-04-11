package org.demo.netty.dispatcher.cache;

import java.util.Collection;

import org.demo.netty.boot.WaiterProvider;
import org.demo.netty.cluster.collection.cache.Cache;
import org.demo.netty.domain.Waiter;
import org.demo.netty.session.NameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月11日 下午5:32:33
 */
public class WaiterCache {

    private static Logger log = LoggerFactory.getLogger(WaiterCache.class);

    private final static Long LOCK_TIMEOUT = 3000L;

    private final static String WAITER_CACHE_PREFIX = "WaiterCache";
    private NameFactory nameFactory;

    public WaiterCache(NameFactory nameFactory) {
        this.nameFactory = nameFactory;
    }

    public Waiter get(String teamCode, String waiterCode) {
        Cache<String, Waiter> caches = getCache(teamCode);
        return caches.get(waiterCode);
    }

    public Collection<Waiter> get(String teamCode) {
        Cache<String, Waiter> caches = getCache(teamCode);
        return caches.values();
    }

    public AcquireResult acquire(String teamCode, String waiterCode) {
        Cache<String, Waiter> caches = getCache(teamCode);
        Waiter waiter;
        boolean threshold = false;
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            waiter = caches.get(waiterCode);
            if (null != waiter) {
                if ("1".equals(waiter.getStatus())) {
                    Integer curReception = waiter.getCurReception();
                    Integer maxReception = waiter.getMaxReception();
                    if (++curReception <= maxReception) {
                        waiter.setCurReception(curReception);
                        if (curReception.equals(maxReception)) {
                            waiter.setSysBusyTimestamp(System.currentTimeMillis());
                            threshold = true;
                        }
                        caches.put(waiterCode, waiter);
                    } else {
                        waiter = null;
                    }
                } else {
                    waiter = null;
                }
            }
        } finally {
            caches.unlock(waiterCode);
        }
        // 同步数据库接待量  与缓存保持一致
        if (null != waiter) {
            WaiterProvider.getInst().updateCurReception(waiter.getId(), 1);
        }

        return new AcquireResult(waiter, threshold);
    }

    public AcquireResult directAcquire(String teamCode, String waiterCode) {
        Cache<String, Waiter> caches = getCache(teamCode);
        Waiter waiter;
        boolean threshold = false;
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            waiter = caches.get(waiterCode);
            if (null != waiter) {
                Integer curReception = waiter.getCurReception();
                waiter.setCurReception(++curReception);
                if (curReception.equals(waiter.getMaxReception())) {
                    waiter.setSysBusyTimestamp(System.currentTimeMillis());
                    threshold = true;
                }
                caches.put(waiterCode, waiter);
            }
        } finally {
            caches.unlock(waiterCode);
        }

        if(waiter != null) {
            // 同步数据库接待量  与缓存保持一致
            WaiterProvider.getInst().updateCurReception(waiter.getId(), 1);
        }
        return new AcquireResult(waiter, threshold);
    }

    public ReleaseResult release(String teamCode, String waiterCode) {
        Cache<String, Waiter> caches = getCache(teamCode);
        Waiter waiter;
        boolean threshold = false;
        long sysBusyTimestamp = 0L;
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            waiter = caches.get(waiterCode);
            if (null != waiter) {
                Integer curReception = waiter.getCurReception();
                waiter.setCurReception(--curReception);
                if (curReception < waiter.getMaxReception()) {
                    sysBusyTimestamp = waiter.getSysBusyTimestamp();
                    waiter.setSysBusyTimestamp(0L);
                    threshold = true;
                }
                caches.put(waiterCode, waiter);
            }
        } finally {
            caches.unlock(waiterCode);
        }
        if (null != waiter) {
            //更新忙碌时长
            if (sysBusyTimestamp > 0) {
                Long time = System.currentTimeMillis() - sysBusyTimestamp;
//                WaiterMonitorProvider.getInst().updateSysBusyTime(teamCode, waiterCode, time / 1000);
            }
            // 同步数据库资源 保持和缓存一致
            WaiterProvider.getInst().updateCurReception(waiter.getId(), -1);
        } else {
            WaiterProvider.getInst().updateCurReceptionByCode(waiterCode, -1);
        }
        return new ReleaseResult(waiter, threshold);
    }

    public void put(String teamCode, Waiter waiter) {
        if (null == teamCode || null == waiter || null == waiter.getWaiterCode()) {
            log.warn("Put WaiterCache params has NULL");
            return;
        }
        String waiterCode = waiter.getWaiterCode();
        Cache<String, Waiter> caches = getCache(teamCode);
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            caches.put(waiterCode, waiter);
        } finally {
            caches.unlock(waiterCode);
        }
    }

    public Waiter remove(String teamCode, String waiterCode) {
        if (null == teamCode || null == waiterCode) {
            log.warn("Put WaiterCache params has NULL");
            return null;
        }
        Waiter waiter;
        Cache<String, Waiter> caches = getCache(teamCode);
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            waiter = caches.remove(waiterCode);
        } finally {
            caches.unlock(waiterCode);
        }
        return waiter;
    }

    public UpdateStatusResult updateStatus(String teamCode, String waiterCode, String status) {
        Cache<String, Waiter> caches = getCache(teamCode);
        Waiter waiter;
        String oldStatus = "";
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            waiter = caches.get(waiterCode);
            oldStatus = waiter.getStatus();
            if (!status.equals(waiter.getStatus())) {
                waiter.setStatus(status);
                caches.put(waiterCode, waiter);
            }
        } finally {
            caches.unlock(waiterCode);
        }

        return new UpdateStatusResult(waiter, oldStatus);
    }

    private Cache<String, Waiter> getCache(String teamCode) {
        if (null == teamCode) {
            log.warn("Get WaiterCache params has NULL");
            return null;
        }
        return nameFactory.getCache(WAITER_CACHE_PREFIX, teamCode);
    }

    public static class UpdateStatusResult {
        private Waiter waiter;

        private String oldStatus;

        public UpdateStatusResult(Waiter waiter, String oldStatus) {
            this.waiter = waiter;
            this.oldStatus = oldStatus;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public void setWaiter(Waiter waiter) {
            this.waiter = waiter;
        }

        public String getOldStatus() {
            return oldStatus;
        }
    }

    public static class AcquireResult {

        private Waiter waiter;
        private boolean threshold;

        public AcquireResult(Waiter waiter, boolean threshold) {
            this.waiter = waiter;
            this.threshold = threshold;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public boolean isThreshold() {
            return threshold;
        }

        public boolean isHas() {
            return this.waiter != null;
        }
    }

    public static class ReleaseResult {

        private Waiter waiter;

        private boolean threshold;

        public ReleaseResult(Waiter waiter, boolean threshold) {
            this.waiter = waiter;
            this.threshold = threshold;
        }

        public Waiter getWaiter() {
            return waiter;
        }

        public void setWaiter(Waiter waiter) {
            this.waiter = waiter;
        }

        public boolean isThreshold() {
            return threshold;
        }

        public boolean isHas() {
            return this.waiter != null;
        }
    }
}
