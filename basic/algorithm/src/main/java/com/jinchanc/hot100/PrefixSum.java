package com.jinchanc.hot100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjin@algorix.co
 * @since 2025/1/14 10:31
 */
public class PrefixSum {

    // 和为k的子数组
    public static int findPrefixSum(int[] nums, int k) {
        List<Integer> prefixSum = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                prefixSum.add(nums[i]);
            } else {
                prefixSum.add(nums[i] + prefixSum.get(i - 1));
            }
        }

        // 左右边界条件理解
        // x - sum = y 条件？
        for (int i = -1; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int sum;
                if (i == -1) {
                    sum = prefixSum.get(j);
                } else {
                    sum = prefixSum.get(j) - prefixSum.get(i);
                }

                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }

    // TODO 还是不是很理解
    public static int findPrefixSum2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int pre = 0;
        int result = 0;
        for (int num : nums) {
            pre += num;
            map.put(pre, map.getOrDefault(pre, 0) + 1);
            if (map.containsKey(k - pre)) {
                result += map.get(k - pre);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(findPrefixSum(new int[]{1, 2, 3}, 3));
    }
}
