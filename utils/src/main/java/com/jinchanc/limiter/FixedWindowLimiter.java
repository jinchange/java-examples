package com.jinchanc.limiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/19 11:21
 * 固定时间窗口限流算法
 * 原理：将时间划分为固定窗口（如1秒），统计窗口内的请求数，超过阈值则限流。
 */
public class FixedWindowLimiter implements Limiter {

    private final int maxQps;
    private final AtomicLong currentCount = new AtomicLong(0);
    private final AtomicLong currentTimeWindow = new AtomicLong(currentTime());

    public FixedWindowLimiter(int maxQps) {
        this.maxQps = maxQps;
    }

    @Override
    public boolean tryAcquire() {
        long c = currentTime();
        if (c != currentTimeWindow.get()) {
            currentTimeWindow.set(c);
            currentCount.set(0);
        }
        return currentCount.incrementAndGet() <= maxQps;
    }

    @Override
    public String toString() {
        return "FixedWindowLimiter{" +
               "maxQps=" + maxQps +
               ", currentCount=" + currentCount +
               ", currentTimeWindow=" + currentTimeWindow +
               '}';
    }
}
