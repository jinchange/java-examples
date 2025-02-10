package com.jinchanc.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/26 17:44
 */
public class CaffeineDemo {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .build();

        cache.put("key", "value");
        System.out.println(cache.get("key", s -> ""));
        cache.invalidateAll();
        System.out.println(cache.getIfPresent("key"));
    }
}
