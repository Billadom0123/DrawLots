package com.example.DrawLots.util;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtils { //引入数组工具,用于开奖时打乱uid数组,这样来随机与奖项配对

    // 打乱传入的 int 数组,用Fisher-Yates Shuffle算法
    public static void shuffleArray(int[] array)
    {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            // 生成一个随机的索引 j
            int j = random.nextInt(i + 1);  // j 取值范围 [0, i]

            // 交换 array[i] 和 array[j]
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    // 反转数组
    public static void reverseArray(int[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    // 查找最大值
    public static int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // 查找最小值
    public static int findMin(int[] array) {
        int min = array[0];
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    // 检查是否包含某个元素
    public static boolean contains(int[] array, int element) {
        for (int num : array) {
            if (num == element) {
                return true;
            }
        }
        return false;
    }

    // 合并两个数组
    public static int[] mergeArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    // 数组转字符串
    public static String arrayToString(int[] array) {
        return Arrays.toString(array);
    }

    // 计算数组元素的和
    public static int sumArray(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    // 检查两个数组是否相等
    public static boolean areArraysEqual(int[] array1, int[] array2) {
        return Arrays.equals(array1, array2);
    }

    // 数组拷贝
    public static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }
}
