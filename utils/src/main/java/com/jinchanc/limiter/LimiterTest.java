package com.jinchanc.limiter;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/19 12:01
 */
public class LimiterTest {

    public static void main(String[] args) throws InterruptedException {
        Limiter limiter = new FixedWindowLimiter(10);
        while (true) {
            if (limiter.tryAcquire()) {
                System.out.println("success");
            } else {
                System.out.println("fail");
            }
            System.out.println(limiter);
            Thread.sleep(80);
        }
    }
}
