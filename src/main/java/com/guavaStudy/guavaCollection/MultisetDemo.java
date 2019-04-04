package com.guavaStudy.guavaCollection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.*;

/**
 * @Description 新集合类型：Multiset
 * @Date 2019/4/4 0004 下午 2:20
 * @Created by Pengrenjun
 */


public class MultisetDemo {


    /**
     * 统计单词出现的次数
     */
    @Test
    public void testWordCount(){
        String strWorld="wer|dffd|ddsa|dfd|dreg|de|dr|ce|ghrt|cf|gt|ser|tg|ghrt|cf|gt|" +
                "ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr|wer|dffd|ddsa|dfd|dreg|de|dr|" +
                "ce|ghrt|cf|gt|ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr";
        String[] words=strWorld.split("\\|");
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        for (String word : words) {
            Integer count = countMap.get(word);
            if (count == null) {
                countMap.put(word, 1);
            }
            else {
                countMap.put(word, count + 1);
            }
        }
        System.out.println("countMap：");
        for(String key:countMap.keySet()){
            System.out.println(key+" count："+countMap.get(key));
        }
    }


    /**
     * 如果使用实现Multiset接口的具体类就可以很容易实现以上的功能需求
     */
    @Test
    public void testMultsetWordCount(){
        String strWorld="wer|dfd|dd|dfd|dda|de|dr|wer";
        String[] words=strWorld.split("\\|");
        List<String> wordList=new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);

        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
    }

    /**
     * Multiset接口的常用方法
     */
    @Test
    public void testMultseMethod(){
        String str = "张三 李四 李四 王五 王五 王五";
        String[] strArr = str.split(" ");

        List<String> words = new ArrayList<String>(Arrays.asList(strArr));

        //创建一个HashMultiset集合，并将words集合数据放入
        Multiset<String> wordMultiset = HashMultiset.create();
        wordMultiset.addAll(words);

        //将不同的元素放在一个集合set中
        for (String key : wordMultiset.elementSet()) {
            //查看指定元素的个数
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("---------向集合中添加元素----------");
        //向集合中添加元素
        wordMultiset.add("李四");
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("-------向集合中添加若干个元素------");
        //向集合中添加若干个元素
        wordMultiset.add("赵六", 10);
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("--------向集合中移出一个元素------");
        //向集合中移出一个元素
        wordMultiset.remove("张三");
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("------向集合中移出若干个元素------");
        //向集合中移出若干个元素,如果元素的个数小于要移除的个数，则会把该元素移除光
        wordMultiset.remove("赵六",5);
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }


        System.out.println("-----设定某一个元素的重复次数-----");
        //设定某一个元素的重复次数
        wordMultiset.setCount("张三", 10);
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("-----设置复合元素的个数设为新的重复次数-----");
        //设置复合元素的个数设为新的重复次数(条件是第二个参数的数量要是实际数量一致，否则无效)
        wordMultiset.setCount("and", 1,0);
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }

        System.out.println("-------删除给定集合中的元素------");
        //删除给定集合中的元素
        wordMultiset.removeAll(words);
        for (String key : wordMultiset.elementSet()) {
            System.out.println(key + "-->" + wordMultiset.count(key));
        }
     }
    }


