package com.guavaStudy.guavaCollection;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

/**
 * @Description TODO
 * @Date 2019/4/4 0004 下午 3:48
 * @Created by Pengrenjun
 */
public class MultimapDemo {

    /**
     * 测试Multimap的修改方法 修改 Multimap
     */
    @Test
    public void testModify(){


        //这里需要注意，所有的guava的集合都有create()方法，这个好处就是比较简单，你不用重复泛型信息了
        Multimap<Integer,String> multimap= LinkedListMultimap.create();
        multimap.put(1,"1a");
        multimap.put(1,"1b");
        multimap.put(1,"1c");

        multimap.put(2,"2d");
        multimap.put(2,"2e");
        multimap.put(2,"2f");


        List<String> strings1 = (List<String>) multimap.get(1);
        System.out.println(strings1.toString());

        //add 对值视图集合进行的修改最终都会反映到底层的 Multimap
        strings1.add("1g");

        //strings2的地址引用和strings1的地址引用是一个
        List<String> strings2 = (List<String>) multimap.get(1);
        System.out.println(strings2.toString());

        //remove
        multimap.remove(1,"1g");

        System.out.println(strings2.toString());

        //putAll
        multimap.putAll(2,strings1);
        System.out.println(multimap.toString());

        //replaceValues
        multimap.replaceValues(1,multimap.get(2));

        System.out.println(multimap.toString());
    }

    /**
     * Multimap 的视图方法
     */
    @Test
    public void  methods(){
        Multimap<Integer,String> multimap= LinkedListMultimap.create();
        multimap.put(1,"1a");
        multimap.put(1,"1b");
        multimap.put(1,"1c");

        multimap.put(2,"2d");
        multimap.put(2,"2e");
        multimap.put(2,"2f");

        // asMap 将multimap转换为传统的结构
        Map<Integer, Collection<String>> integerCollectionMap = multimap.asMap();
        System.out.println(integerCollectionMap.toString());

        // 返回的integerCollectionMap为Multimaps类 不支持put操作；
        //integerCollectionMap.put(3,multimap.get(1));

        //返回空的集合
        Collection<String> strings = multimap.get(3);
        System.out.println(strings.size());

        //返回null
        Collection<String> strings1 = integerCollectionMap.get(3);
        System.out.println(strings1);

        // entries 用 Collection<Map.Entry<K, V>>返回 Multimap 中所有”键-单个值映射”——包括重复键
        Collection<Map.Entry<Integer, String>> entries = multimap.entries();
        System.out.println(entries.toString());


        //keySet用 Set 表示 Multimap 中所有不同的键
        Set<Integer> integers = multimap.keySet();
        System.out.println(integers.toString());

        System.out.println(integerCollectionMap.entrySet().toString());

        //keys 用 Multiset 表示 Multimap 中的所有键，每个键重复出现的次数等于它映射的值的个数。可以从这个
        //Multiset 中移除元素，但不能做添加操作；移除操作会反映到底层的 Multimap
        Multiset<Integer> keys = multimap.keys();
        System.out.println(keys.toString());

        // values()

        Collection<String> values = multimap.values();
        System.out.println(values.toString());

        System.out.println(multimap.size());
        System.out.println(multimap.keySet().size());
    }

}
