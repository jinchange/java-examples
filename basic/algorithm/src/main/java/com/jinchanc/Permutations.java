package com.jinchanc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {
    // 给定一个不含重复数字的数组，返回其所有可能的全排列。
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>(); // 存储所有排列结果
        backtrack(nums, new ArrayList<>(), result);    // 调用回溯函数
        return result;
    }

    private void backtrack(int[] nums, List<Integer> path, List<List<Integer>> result) {
        // 如果当前路径的长度等于数组长度，说明找到一个排列
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path)); // 将当前路径加入结果集
            return;
        }

        // 遍历数组中的每个数字
        for (int num : nums) {
            if (path.contains(num)) {
                continue; // 如果当前数字已经在路径中，跳过（避免重复）
            }
            path.add(num); // 将当前数字加入路径
            backtrack(nums, path, result); // 递归调用，继续选择下一个数字
            path.removeLast(); // 回溯：撤销选择，移除最后一个数字
        }
    }

    public static void main(String[] args) {
//        Permutations solution = new Permutations();
//        int[] nums = {1, 2, 3};
//        List<List<Integer>> result = solution.permute(nums);
//        System.out.println(result);
        int[] ints = {3,4,5,6,7,10,1,2,6,8,9};
        insertionSort(ints);
        System.out.println(Arrays.toString(ints));
    }

    //冒泡排序 O(n) ~ O(n^2)
    public static void bubbleSort(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            boolean flag = true;
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    flag = false;
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }

            if (flag) {
                break;
            }
        }
    }

    public static void selectionSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
    }

    public static void insertionSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0; j--) {
                if (nums[j] < nums[j-1]) {
                    int temp = nums[j-1];
                    nums[j-1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

}