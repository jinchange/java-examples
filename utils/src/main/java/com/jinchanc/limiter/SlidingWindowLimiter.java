package com.jinchanc.limiter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 475636591@qq.com
 * @since 2025/3/19 15:02
 * 滑动时间窗口限流算法
 */
public class SlidingWindowLimiter {
    private final int maxQps;
    private final List<Long> latestOneSecondsWindow = new ArrayList<>();

    public SlidingWindowLimiter(int maxQps) {
        this.maxQps = maxQps;
    }

    public synchronized boolean tryAcquire() {
        long currentTimeMillis = System.currentTimeMillis();
        latestOneSecondsWindow.removeIf(item -> item < currentTimeMillis - 1000);
        if(latestOneSecondsWindow.size() < maxQps) {
            latestOneSecondsWindow.add(currentTimeMillis);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowLimiter slidingWindowLimiter = new SlidingWindowLimiter(5);
        while (true) {
            if (slidingWindowLimiter.tryAcquire()) {
                System.out.println("acquire " + LocalDateTime.now());
            } else {
                System.out.println("block " + LocalDateTime.now());
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
