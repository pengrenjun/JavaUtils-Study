package com.guavaStudy.guavaCollection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import com.guavaStudy.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 不可变集合
 * @Date 2019/4/2 0002 下午 7:18
 * @Created by Pengrenjun
 */
public class ImmutableCollectionDemo {


    /**
     * 为什么要使用不可变集合
     *
     * 不可变对象有很多优点，包括：
     * • 当对象被不可信的库调用时，不可变形式是安全的；
     * • 不可变对象被多个线程调用时，不存在竞态条件问题
     * • 不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内
     * 存利用率（分析和测试细节）；
     * • 不可变对象因为有固定不变，可以作为常量来安全使用。
     *
     * JDK也提供了Collections.unmodifiableXXX方法把集合包装为不可变形式，但我们认为不够好：
     * 笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景； 不安全：要保证没人通过原集合的引用进行修改，
     * 返回的集合才是事实上不可变的； 低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等。
      */
  @Test
  public void  testImmutableSet(){

      ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
              "red",
              "orange",
              "yellow",
              "green",
              "blue",
              "purple");

      /**
       *   Guaranteed to throw an exception and leave the collection unmodified.
       *   public final boolean add(E e) {
       *     throw new UnsupportedOperationException();
       *   }
       */
      //COLOR_NAMES.add("qwe");

      //构造方法一: copyOf方法
      List<String> list= Arrays.asList("1","3","2","2");
      ImmutableList<String> immutableList = ImmutableList.copyOf(list);
      immutableList.forEach(System.out::println);
      System.out.println("-----------------------");
      //构造方法二: of方法
      ImmutableSet<String> immutableSet2 = ImmutableSet.of("a", "c", "b");
      immutableSet2.forEach(System.out::println);
      System.out.println("-----------------------");
      //构造方法三: Builder工具
      ImmutableSet<Person> immutableSet3 = ImmutableSet.<Person>builder()
              .addAll(Person.personList)
              .build();
      immutableSet3.forEach(System.out::println);
      System.out.println("-----------------------");


      //此外，对有序不可变集合来说，排序是在构造集合的时候完成的，如：
      ImmutableSortedSet<String> sortedSet = ImmutableSortedSet.of("a", "c", "b", "a", "d", "b","c");
      UnmodifiableIterator<String> iterator = sortedSet.iterator();
      while(iterator.hasNext()){
          System.out.println(iterator.next());//a, b, c, d
      }

      System.out.println("-----------------------");
      //asList视图
      ImmutableList<String> immutableList1 = sortedSet.asList();
      System.out.println(immutableList1.toString());
  }
}
