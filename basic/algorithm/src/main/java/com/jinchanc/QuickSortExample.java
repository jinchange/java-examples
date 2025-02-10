package com.jinchanc;

import java.util.Arrays;

public class QuickSortExample {

    public static void main(String[] args) {
        int[] array = {7, 2, 9, 1, 3}; // 示例数组
        System.out.println("排序前的数组: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("排序后的数组:" + Arrays.toString(array));
    }

    // 快速排序方法
    public static void quickSort(int[] array, int low, int high) {
        // 直到数组长度 <= 1
        if (low < high) {
            // pi 是分区操作后基准元素的索引
            int pi = partition(array, low, high);
            // 分别对基准元素左右两侧的子数组进行递归排序
            quickSort(array, low, pi - 1); // 左侧子数组
            quickSort(array, pi + 1, high); // 右侧子数组
        }
    }

    // 分区操作
    public static int partition(int[] array, int low, int high) {
        int pivot = array[high]; // 默认选择最后一个元素作为基准
        int i = low - 1; // i 是比区间最小索引还小1的索引
        for (int j = low; j < high; j++) {
            // 如果当前元素小于或等于基准
            if (array[j] <= pivot) {
                i++;
                // 交换 array[i] 和 array[j]
                swap(array, i, j);
            }
        }

        // 把基准放到正确的位置
        swap(array, i + 1, high);
        return i + 1;
    }

    // 交换数组中的两个元素
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}