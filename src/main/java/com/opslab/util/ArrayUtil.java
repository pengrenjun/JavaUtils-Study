package com.opslab.util;

import org.junit.Assert;

import java.util.*;

/**
 * 数组相关的工具类
 */
public class ArrayUtil  {
    //更多方法参考java自带的Arrays类中的方法


    /**
     * 获取一个double类型的数字的小数位有多长
     * @param dd
     * @return
     */
    public static int doueleBitCount(double dd){
        String temp = String.valueOf(dd);
        int i = temp.indexOf(".");
        if(i > -1){
            return temp.length()-i -1;
        }
        return 0;

    }

    public static Integer[] doubleBitCount(double[] arr){
        Integer[] len = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            len[i] = doueleBitCount(arr[i]);
        }
        return len;
    }

    /**
     * 将数组转化为List集合
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> List<T> convertArrToList(T[] arr){
       if(ObjectUtils.isNotNull(arr)){
           return Arrays.asList(arr);
       }else {
           return null;
       }

    }

    /**
     * 将数组转换为Set集合
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> Set<T> convertArrToSet(T[] arr){
        if(ObjectUtils.isNotNull(arr)){
            return new HashSet<>(Arrays.asList(arr)) ;
        }else {
            return null;
        }

    }

    /**
     * 将数组进行排序
     * @param arr
     */
    public static  void sort(Object[] arr){
         Arrays.sort(arr);
    }

    /**
     * 查询某值在数组中是否存在 若返回值小于0则不存在
     * @param arr
     * @param key
     * @param <T>
     * @return
     */
    public static <T> int search(T[] arr,T key){
       int loc=Arrays.binarySearch(arr,0,arr.length,key) ;
       return  loc;
    }

    /**
     * 两个数组是否相等
     * @param arr1
     * @param arr2
     * @param seqorder 元素的位置是否作为判断条件
     * @param <T>
     * @return
     */
    public static <T> boolean equals(T[]arr1,T[] arr2,Boolean seqorder){
        if(seqorder){
            return  Arrays.equals(arr1,arr2);
        }

        if (arr1==arr2){
            return true;
        }
        if (arr1==null || arr2==null){
            return false;
        }

        int length = arr1.length;
        if (arr2.length != length){
            return false;
        }
        T[]ARR1=arr1.clone();T[] ARR2=arr2.clone();
        sort(ARR1);sort(ARR2);
        return equals(ARR1,ARR2,true);
    }


    public static void main(String[] args) {
        Object[] str1={1,4,2,5,6,3,5};

        Object[] str2={1,4,2,5,6,5,3};








    }
}
