package com.jinchanc;

import java.util.Arrays;

/**
 * @author zhangjin@algorix.co
 * @since 2025/2/8 17:19
 */
public class QuickSortLeft {

    public static void main(String[] args) {
        int[] array = {7, 2, 9, 1, 3}; // 示例数组
//        System.out.println("排序前的数组: " + Arrays.toString(array));
//        quickSort(array, 0, array.length - 1);
//        System.out.println("排序后的数组:" + Arrays.toString(array));

        System.out.println(partition(array, 0, array.length - 1));
        System.out.println(Arrays.toString(array));
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[low];
        int i = low;
        for (int j = low + 1; j <= high; j++) {
            if (array[j] <= pivot) {
                swap(array, i, j);
                System.out.println(Arrays.toString(array));
                i++;
            }
        }
//        swap(array, low, i);
//        System.out.println(Arrays.toString(array));
        return i;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
