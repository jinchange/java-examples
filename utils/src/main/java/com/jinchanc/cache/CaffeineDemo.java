package com.jinchanc.cache;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/26 17:44
 */
public class CaffeineDemo {
    public static void main(String[] args) {
        cacheExpireDemo();
    }

    public static void cacheExpireDemo() {

        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(10)) // 最后一次访问后十秒自动过期删除
                .expireAfterWrite(Duration.ofSeconds(10))  // 写入后十秒自动过期删除
                .expireAfter(new Expiry<Object, Object>() { // 自定义过期策略
                    @Override
                    public long expireAfterCreate(Object key, Object value, long currentTime) {
                        return Duration.ofSeconds(100).toNanos();
                    }

                    @Override
                    public long expireAfterUpdate(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                }).build();

        // 其他配置
        Cache<Object, Object> cache2 = Caffeine.newBuilder()
                .executor(Executors.newVirtualThreadPerTaskExecutor()) // 指定异步加载的执行器
                .recordStats() // 记录每个缓存的状态
                .weakKeys() // 指定key为弱引用
                .weakValues() // 指定value为弱引用
                .softValues() // 指定value为软引用
                .initialCapacity(100) // 初始容量
                .maximumSize(100) // 指定cache的最大尺寸
                .maximumWeight(1) // 权重
                .evictionListener((key, value, cause) -> {
                    System.out.println(key + ": " + value + cause.toString());
                }) // 缓存驱逐监听器
                .build();

    }

    private static void cacheLoaderDemo() {
        // 同步加载缓存，支持同步的自动加载key，对应的value
        Cache<String, String> cache = Caffeine.newBuilder()
                .build();
        // 在get方法时定义加载逻辑
        String value = cache.get("key1", key -> {
            System.out.println("同步加载");
            return "defaultValue";
        });
        System.out.println(value);

        // 在build创建时定义CacheLoader 缓存加载器定义加载逻辑，重写的方法更多更强大
        LoadingCache<String, String> loadingCache = Caffeine.newBuilder()
                .build(new CacheLoader<>() {
                    @Override
                    public @Nullable String load(String key) throws Exception {
                        return key + "+defaultValue";
                    }

                    @Override
                    public Map<String, String> loadAll(Set<? extends String> keys) throws Exception {
                        Map<String, String> map = new HashMap<>();
                        for (String key : keys) {
                            map.put(key, load(key));
                        }
                        return map;
                    }
                });
        System.out.println(loadingCache.get("key"));

        // 异步加载缓存, 异步自动加载缓存，返回Future
        AsyncCache<String, String> asyncCache = Caffeine.newBuilder()
                .buildAsync();
        CompletableFuture<String> future = asyncCache.get("key", new Function<String, String>() {
            @Override
            public String apply(String string) {
                System.out.println(Thread.currentThread().getName());
                return string + " defaultValue";
            }
        });
        System.out.println(future.join());

        AsyncLoadingCache<String, String> asyncLoadingCache = Caffeine.newBuilder()
                .buildAsync(new AsyncCacheLoader<String, String>() {
                    @Override
                    public CompletableFuture<? extends String> asyncLoad(String key, Executor executor) throws Exception {
                        return CompletableFuture.supplyAsync(new Supplier<String>() {
                            @Override
                            public String get() {
                                System.out.println(Thread.currentThread().getName());
                                return key + "+AsyncCacheLoader";
                            }
                        }, executor);
                    }
                });

        CompletableFuture<String> f = asyncLoadingCache.get("key");
        System.out.println(f.join());
    }
}
