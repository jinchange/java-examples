package com.jinchanc;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        int[] nums = new int[]{1, 2, 3, 4};
//        int k = 2;
//        System.out.println(Arrays.toString(f2(nums, k)));
        int[][] l = new int[][]{{1, 9}, {3, 8}, {4, 5}};
        int[][] l1 = f(l);
        System.out.println(Arrays.toString(l1));
    }

    public static int[][] f(int[][] matrix) {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int[] ints : matrix) {
            int l = ints[0];
            int r = ints[1];
            if (Objects.equals(map.get(l), "r")) {
                map.remove(l);
            } else {
                map.put(l, map.getOrDefault(l,"") +"l");
            }
            if (Objects.equals(map.get(r), "l")) {
                map.remove(r);
            } else {
                map.put(r, map.getOrDefault(r,"") +"r");
            }
        }

        List<int[]> list = new ArrayList<>();
        int lv = 0, rv = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int[] item = new int[2];
            if (entry.getValue().contains("l")) {
                lv += entry.getValue().length();
            }
            if (entry.getValue().contains("r")) {
                rv += entry.getValue().length();
            }
            if (lv == 0) {
                item[0] = entry.getKey();
            }
            if (lv == rv) {
                item[1] = entry.getKey();
                lv = 0;
                rv = 0;
                list.add(item);
                System.out.println(Arrays.toString(item));
            }
        }
        return list.toArray(new int[0][]);
    }

//    // 需要用到额外空间
//    public static int[] f(int[] nums, int k) {
//        int[] result = new int[nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            result[(i + k) % nums.length] = nums[i];
//        }
//        return result;
//    }
//
//    // 不使用额外空间
//    public static int[] f2(int[] nums, int k) {
////        for (int i = 0; i < k; i++) {
//            for (int j = 0; j < nums.length; j++) {
//                int temp = nums[j+1];
//                nums[j+1] = nums[j];
//
//            }
////        }
//        return nums;
//    }
}

