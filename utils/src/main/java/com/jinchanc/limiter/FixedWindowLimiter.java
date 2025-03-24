package com.jinchanc.limiter;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/19 11:21
 * 固定时间窗口限流算法
 * 原理：将时间划分为固定窗口（如1秒），统计窗口内的请求数，超过阈值则限流。
 */
public class FixedWindowLimiter {

    private final int maxQps;
    private long currentCount = 0;
    private long currentTimeSecond = 0;

    public FixedWindowLimiter(int maxQps) {
        this.maxQps = maxQps;
    }

    public synchronized boolean tryAcquire() {
        // 更新时间窗口
        long t = System.currentTimeMillis() / 1000;
        if (currentTimeSecond != t) {
            currentTimeSecond = t;
            currentCount = 0;
        }

        // 判断是否达到 maxQps
        if (currentCount <= maxQps) {
            currentCount++;
            return true;
        } else {
            return false;
        }
    }
}
