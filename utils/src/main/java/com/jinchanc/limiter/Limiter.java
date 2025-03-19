package com.jinchanc.limiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangjin@algorix.co
 * @since 2025/3/19 11:16
 */
public interface Limiter {

    /**
     * 尝试获取资源
     * @return true 成功获取到资源，false 超出限制数获取失败
     */
    boolean tryAcquire();

    default long currentTime() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}
