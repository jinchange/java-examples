package com.jinchanc.limiter;

import java.util.concurrent.*;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/21 10:46
 * 漏统算法 TODO
 */
public class LeakyBucketLimiter {
    private final long capacity;          // 桶的容量（最多允许积压的请求数）
    private final long leakRatePerMs;     // 漏水的速率（每毫秒漏出的请求数）
    private long waterLevel = 0;          // 当前桶中的水量（积压的请求数）
    private long lastLeakTime;            // 上次漏水的时间戳（毫秒）

    public LeakyBucketLimiter(long capacity, long leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerMs = (long) (leakRatePerSecond / 1000.0); // 转换为毫秒级速率
        this.lastLeakTime = System.currentTimeMillis();
    }

    /**
     * 检查请求是否允许通过（同步方法）
     */
    public synchronized boolean tryAcquire() {
        // 1. 计算自上次漏水后漏出的水量
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastLeakTime;
        double leakedWater = elapsedTime * leakRatePerMs;

        // 2. 更新桶中水量（不能小于0）
        waterLevel = (long) Math.max(0, waterLevel - leakedWater);
        lastLeakTime = currentTime;

        // 3. 检查桶是否还有空间容纳新请求
        if (waterLevel < capacity) {
            waterLevel++;
            return true; // 允许请求
        } else {
            return false; // 限流
        }
    }

    // 测试代码
    public static void main(String[] args) throws InterruptedException {
        // 示例：桶容量为5，漏水速率为每秒2个请求（即每500ms处理一个）
        LeakyBucketLimiter limiter = new LeakyBucketLimiter(5, 1000);
        System.out.println(limiter.leakRatePerMs);

        // 模拟突发请求（连续发送10个请求）
        for (int i = 0; i < 10; i++) {
            boolean allowed = limiter.tryAcquire();
            System.out.println("Request " + (i + 1) + ": " +
                               (allowed ? "Allowed" : "Blocked") +
                               " | Water Level: " + limiter.waterLevel);

            TimeUnit.MILLISECONDS.sleep(100); // 每隔100ms发送一个请求
        }
    }
}
