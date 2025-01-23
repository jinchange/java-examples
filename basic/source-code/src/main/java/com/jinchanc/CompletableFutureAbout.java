package com.jinchanc;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/22 14:58
 */
public class CompletableFutureAbout {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> asyncResult = CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                    return UUID.randomUUID().toString();
                })
                .thenApplyAsync(val -> {
                    System.out.println(Thread.currentThread().getName());
                    if (Math.random() > 0.5) {
                        throw new RuntimeException("Something went wrong");
                    }
                    return val + " thenApply";
                }).exceptionallyAsync(throwable -> {
                    System.out.println(Thread.currentThread().getName());
                    return "exceptionally";
                });
        System.out.println(asyncResult.get());
    }
}
