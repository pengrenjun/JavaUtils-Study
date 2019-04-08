package com.guavaStudy.guavaCollection;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/4/6.
 */
public class CollectionsUtilsDemo {


    /**
     * 静态工厂方法
     */
    @Test
    public void testStaticMethos(){
        //Guava 提供了能够推断范型的静态工厂方法
        List<Integer> list = Lists.newArrayList();
        Map<Integer, String> map = Maps.newLinkedHashMap();

        //但 Guava 的静态工厂方法远不止这么简单。用工厂方法模式，我们可以方便地在初始化时就指定起始元素。
        Set<Integer> copySet = Sets.newHashSet(1,2,3);
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");

        //此外，通过为工厂方法命名（Effective Java 第一条），我们可以提高集合初始化大小的可读性：
        //指定大小 提高性能 填写的元素个数超过限制会自动进行扩容
        List<Integer> exactly100 = Lists.newArrayListWithCapacity(100);
        List<Integer> approx100 = Lists.newArrayListWithExpectedSize(100);
        Set<Integer> approx100Set = Sets.newHashSetWithExpectedSize(100);

        for(int i=0;i<=1000;i++){
            exactly100.add(i);
            approx100.add(i);
        }
        System.out.println(exactly100.toString());
        System.out.println(approx100.toString());

        //注意：Guava 引入的新集合类型没有暴露原始构造器，也没有在工具类中提供初始化方法。而是直接在集合类中
        //提供了静态工厂方法，例如：
        Multiset<String> multiset = HashMultiset.create();
    }

    /**
     * Lists
     */
    @Test
    public  void testLists(){

        List countUp = Ints.asList(1, 2, 3, 4, 5);
        List countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}
        //把 List 按指定大小分割
        List<List> parts = Lists.partition(countUp, 2);//{{1,2}, {3,4}, {5}}
        System.out.println(parts.toString());

    }

    /**
     * Sets
     */
    @Test
    public void testSets(){

        /**
         * 我们提供了很多标准的集合运算（Set-Theoretic）方法，这些方法接受 Set 参数并返回 SetView，可用于：
         • 直接当作 Set 使用，因为 SetView 也实现了 Set 接口；
         • 用 copyInto(Set) 拷贝进另一个可变集合；
         • 用 immutableCopy()对自己做不可变拷贝
         * 方法
         * union(Set, Set) 合集
         * intersection(Set, Set) 交集
         * difference(Set, Set) 补集
         * symmetricDifference(Set, Set)
         */
        Set<String> wordsWithPrimeLength = ImmutableSet.of("one", "two", "three", "six", "seven", "eight");
        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        Sets.SetView<String> intersection = Sets.intersection(primes,wordsWithPrimeLength);
        // intersection包含"two", "three", "seven"
        ImmutableSet<String> strings = intersection.immutableCopy();//可以使用交集，但不可变拷贝的读取效率更高


        /**
         * 其他 Set 工具方法
         方法 描述 另请参见
         cartesianProduct(List<Set>) 返回所有集合的笛卡儿积 cartesianProduct(Set...)
         powerSet(Set) 返回给定集合的所有子集
         */
        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");

        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);// {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"}, {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}

        Set<Set<String>> animalSets = Sets.powerSet(animals);// {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}

        System.out.println(product);
        System.out.println(animalSets);

    }

    @Test
    public void testMaps(){

        System.out.println("========Maps.uniqueIndex(Iterable,Function) uniqueIndex =========");

        List<String> stringList=Lists.newArrayList("a","bc","qwe","asdzx","weqasdzxc");

        Function<? super String, Integer> keyfunction=new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        //对集合stringList根据其唯一的特性：长度 进行分类
        Map<Integer,String> integerStringMap=Maps.uniqueIndex(stringList,keyfunction);
        System.out.println(integerStringMap);

        //索引值不是独一无二的，请参见下面的 Multimaps.index 方法
        Function<? super String, String> mulKeyfunction=new Function<String, String>() {
            @Override
            public String apply(String s) {
                if(s.contains("a")){
                    return  "contains a";
                }

                return  "no contains a";
            }
        };

        Multimap<String,String> stringMultimap= Multimaps.index(stringList,mulKeyfunction);

        System.out.println(stringMultimap.toString());

        System.out.println("===================== difference =================");
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("a", 2, "b", 2, "c", 3,"d",5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        Map<String, Integer> stringIntegerMap = diff.entriesInCommon();
        System.out.println(stringIntegerMap);

        Map<String, MapDifference.ValueDifference<Integer>> stringValueDifferenceMap = diff.entriesDiffering();
        System.out.println(stringValueDifferenceMap);

    }

    @Test
    public void testMultimaps(){

        System.out.println("================= index =============================");

        //index
        ImmutableSet digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };
        ImmutableListMultimap<Integer, String> digitsByLength= Multimaps.index(digits, lengthFunction);
        System.out.println(digitsByLength.toString());

        System.out.println("=============== invertFrom =========================");
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));
        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.create());

        System.out.println(inverse.toString());
        //注意我们选择的实现，因为选了TreeMultimap，得到的反转结果是有序的
        /*
        * inverse maps:
        * 1 => {"a"}
        * 2 => {"a", "b", "c"}
        * 3 => {"c"}
        * 4 => {"a", "b"}
        * 5 => {"c"}
        * 6 => {"b"}
        */


        System.out.println("================== forMap ========================");
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        SetMultimap<String, Integer> setMultimap = Multimaps.forMap(map);
        System.out.println(setMultimap.toString());
// multimap：["a" => {1}, "b" => {1}, "c" => {2}]
        Multimap<Integer, String> stringMultimap = Multimaps.invertFrom(setMultimap, HashMultimap.create());
        System.out.println(stringMultimap.toString());
// inverse：[1 => {"a","b"}, 2 => {"c"}]

    }

}
