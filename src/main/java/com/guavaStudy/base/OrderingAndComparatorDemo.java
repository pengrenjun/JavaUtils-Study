package com.guavaStudy.base;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.guavaStudy.Person;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import java.util.List;

/**
 * @Description 排序: Guava 强大的流畅风格比较器
 * @Date 2019/4/2 0002 下午 5:25
 * @Created by Pengrenjun
 */

/**
 * 排序器[Ordering]是 Guava 流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集
 * 合排序的功能。
 * 从实现上说，Ordering 实例就是一个特殊的 Comparator 实例。Ordering 把很多基于 Comparator 的静态方
 * 法（如 Collections.max）包装为自己的实例方法（非静态方法），并且提供了链式调用方法，来定制和增强现
 * 有的比较器。
 *
 */
public class OrderingAndComparatorDemo {


    /**
     * 创建排序器：常见的排序器可以由下面的静态方法创建
     * natural() 对可排序类型做自然排序，如数字按大小
     * usingToString() 按对象的字符串形式做字典排序[lexicog
     * from(Comparator) 把给定的 Comparator 转化为排
     */
    @Test
    public void testOrderingCreat(){
        List<Person> personList=Person.personList;


        //Person需要实现Comparator接口 自定义比较策略
        //创建排序器 (基于排序的对象实现了Comparator接口)
        Ordering<Person> personOrdering= Ordering.natural().reverse();

        Ordering<Person> personOrdering2=Ordering.from(Person::compareTo);

        List<Person> personList1 = personOrdering.sortedCopy(personList);

        List<Person> personList2 = personOrdering2.sortedCopy(personList);


        System.out.println(personList1.toString());
        System.out.println(personList2.toString());


        //跳过实现 Comparator，而直接继承 Ordering
        Ordering<Person> byWorkDateOrdering = new Ordering<Person>() {
            @Override
            public int compare(@Nullable Person left, @Nullable Person right) {
                if(left.getWorkDate().equals(right.getWorkDate())){
                    return 0;
                }

                if(left.getWorkDate().before(right.getWorkDate())){
                    return -3;
                }
                return  1;
            }
        };

        List<Person> personList3 = byWorkDateOrdering.sortedCopy(personList);

        System.out.println(personList3);
    }

    /**
     * 链式调用方法：通过链式调用，可以由给定的排序器衍生出其它排序器
     * 方法 描述
     * reverse() 获取语义相反的排序器
     * nullsFirst() 使用当前排序器，但额外把 null 值排到最前面。
     * nullsLast() 使用当前排序器，但额外把 null 值排到最后面。
     * compound(Comparator) 合成另一个比较器，以处理当前排序器中的相等情况。
     * lexicographical() 基于处理类型 T 的排序器，返回该类型的可迭代对象 Iterable<T>的排序
     * 器。
     * onResultOf(Function) 对集合中元素调用 Function，再按返回值用当前排序器排序
     */
    @Test
    public void testChainOrdring(){

        //超过一定长度的链式调用，也可能会带来阅读和理解上的难度。我们建议按下面的代码这样，在一个链中最多使
        //用三个方法。此外，你也可以把 Function 分离成中间对象，让链式调用更简洁紧凑
        Function<Person, String> sortFunction=new Function<Person, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Person input) {
                return input.getFirstName();
            }
        };

        //当阅读链式调用产生的排序器时，应该从后往前读。上面的例子中，排序器首先调用 apply 方法获取 sortedBy
        //值，并把 sortedBy 为 null 的元素都放到最前面，然后把剩下的元素按 sortedBy 进行自然排序。之所以要从后
        //往前读，是因为每次链式调用都是用后面的方法包装了前面的排序器。
        Ordering<Person> ordering = Ordering.natural().nullsFirst().onResultOf(sortFunction);


    }

    /**
     * 运用排序器：Guava 的排序器实现有若干操纵集合或元素值的方法
     *
     * 方法 描述 另请参见
     * greatestOf(Iterable
     * iterable, int k)
     * 获取可迭代对象中最大的k个元素。 leastOf
     * isOrdered(Iterable) 判断可迭代对象是否已按排序器排序：允许有排序
     * 值相等的元素。
     * isStrictlyOrdered
     * sortedCopy(Iterabl
     * e)
     * 判断可迭代对象是否已严格按排序器排序：不允许
     * 排序值相等的元素。
     * immutableSortedCopy
     * min(E, E) 返回两个参数中最小的那个。如果相等，则返回第
     * 一个参数。
     * max(E, E)
     * min(E, E, E, E...) 返回多个参数中最小的那个。如果有超过一个参数
     * 都最小，则返回第一个最小的参数。
     * max(E, E, E, E...)
     * min(Iterable) 返回迭代器中最小的元素。如果可迭代对象中没有
     * 元素，则抛出 NoSuchElementException。
     * max(Iterable), min(Iter
     * ator), max(Iterator)
     */
    @Test
    public void testOrderCollection(){

        List<Person> personList=Person.personList;

        //Person需要实现Comparator接口 自定义比较策略
        //创建排序器 (基于排序的对象实现了Comparator接口)
        Ordering<Person> personOrdering= Ordering.natural().reverse();

        List<Person> personList1 = personOrdering.greatestOf(personList, 2);
        System.out.println(personList1);

    }



}
