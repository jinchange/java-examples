package com.jinchanc.limiter;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/19 15:02
 * 滑动时间窗口限流算法
 */
public class SlidingWindowLimiter implements Limiter{
    @Override
    public boolean tryAcquire() {
        return false;
    }
}
