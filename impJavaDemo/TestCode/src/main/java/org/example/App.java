package org.example;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    /**
     * 给定一个数 n 比如 23412
     * 给定一个数组 [2 4 9]
     * 找出这个数组能组成的第一个小于这个数的数
     */
    public static void main(String[] args) {
        int[] narray = new int[]{22221, 21132, 24132, 24132, 14132, 14132, 1, 1000};
        int[][] array = new int[][]{{2}, {2, 4, 9}, {2, 4, 9}, {1, 4, 9}, {2, 4, 9}, {1, 4, 9}, {2, 4, 9}, {2, 4, 9}};

        for (int i = 0; i < narray.length; i++) {
            System.out.println(findNumLessN(narray[i], array[i]));
        }
    }

    public static int makeans(int[] array, int[] res, int k, int len, int alen){
        int ans = 0;
        for (int j = 0; j <= k; j ++){
            ans += array[res[j]];
            ans = ans * 10;
        }
        for (int j = k + 1; j < len; j ++){
            ans += array[alen - 1];
            ans = ans * 10;
        }
        ans /= 10;
        return ans;
    }

    public static int findNumLessN(int n, int[] array){
        int num = n;
        String s = Integer.toString(num);
        Arrays.sort(array);
        // 取当前array中<=k的最大值作为当前数
        // 如果 <,  则后续都取array最大值 end
        // 如果 =， 则后续仍需判断
        // 如果出现array中最小值仍大于k
        // 1. 将前一个k对应的array中的值取一个小值，后续取最大值
        // 2. 如果取不到小值，则再往前判断
        int len = s.length();
        int alen = array.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i ++) {
            boolean flag = false;
            for (int j = 0; j < alen; j++) {
                if (array[j] <= s.charAt(i) - '0') {
                    flag = true;
                    // 记录的下标
                    res[i] = j;
                } else {
                    break;
                }
            }
            if (!flag) {
                int k = i - 1;
                while (k >= 0 && res[k] == 0) {
                    k--;
                }
                // 没找到
                if (k == -1) {
                    return makeans(array, res, -1, len - 1, alen);
                } else {
                    // 找到
                    res[k] --;
                    return makeans(array, res, k, len, alen);
                }
            }else {
                if (array[res[i]] < s.charAt(i) - '0') {
                    return makeans(array, res, i, len, alen);
                }
            }
        }
        // 全部匹配
        // 完全一样和无答案
        int k = res[len - 1];
        if (k == 0 && len == 1){
            return -1;
        }
        return makeans(array, res, -1, len - 1, alen);
    }
}
