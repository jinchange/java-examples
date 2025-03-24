package com.jinchanc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        // 强引用：只有跟GC root 断绝关系才会被回收
//        Object object = new Object();
//        // 软引用：内存不足时被回收
//        SoftReference<Object> softReference = new SoftReference<>(object);
//        // 弱引用：发生GC时就会被回收
//        WeakReference<Object> weakReference = new WeakReference<>(object);
//        // 虚引用：随时都可能被垃圾回收，必须和引用队列（ReferenceQueue）联合使用，get 返回null
//        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
//        PhantomReference<Object> phantomReference = new PhantomReference<>(object, referenceQueue);
//        System.out.println(phantomReference.get());

        System.out.println(f(List.of(1,1)));
    }

    public static List<List<Integer>> f(List<Integer> list) {
        Map<Integer,Integer> map = new HashMap<>();
        for (Integer i : list) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        List<List<Integer>> result = new ArrayList<>();
        while (!map.isEmpty()) {
            Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
            List<Integer> item = new ArrayList<>();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Integer> entry = iterator.next();
                Integer k = entry.getKey();
                Integer count = entry.getValue();
                if (count == 1) {
                    iterator.remove();
                } else {
                    entry.setValue(count - 1);
                }
                item.add(k);
            }
            result.add(item);
        }
        return result;
    }
}