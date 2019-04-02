package com.guavaStudy.base;


import com.google.common.base.Optional;
import java.util.Set;


/**
 * @Description guava optional工具类的简单使用介绍
 * @Date 2019/3/29 0029 下午 3:34
 * @Created by Pengrenjun
 */
public class optionalDemo {

    /**
     * 使用 Optional 的意义在哪儿？
     * 使用 Optional 除了赋予 null 语义，增加了可读性，最大的优点在于它是一种傻瓜式的防护。Optional 迫使你积
     * 极思考引用缺失的情况，因为你必须显式地从 Optional 获取引用。直接使用 null 很容易让人忘掉某些情形，尽
     * 管 FindBugs 可以帮助查找 null 相关的问题，但是我们还是认为它并不能准确地定位问题根源。
     *
     * 如同输入参数，方法的返回值也可能是 null。和其他人一样，你绝对很可能会忘记别人写的方法 method(a,b)会
     * 返回一个 null，就好像当你实现 method(a,b)时，也很可能忘记输入参数 a 可以为 null。将方法的返回类型指定
     * 为 Optional，也可以迫使调用者思考返回的引用缺失的情形
     */

    public static void main(String[] args) {
        //option实例的创建
        testA();
    }

    //一个Optional 实例可能包含非 null 的引用（我们称之为引
    //用存在），也可能什么也不包括（称之为引用缺失）。它从不说包含的是 null 值，而是用存在或缺失来表示。但
    //Optional 从不会包含 null 值引用
    private static void testA() {

        Integer a=null;
        //public static <T> Optional<T> of(T reference) {
        //    return new Present<T>(checkNotNull(reference));
        //  }

        //  java.lang.NullPointerException
        // Optional<Integer> optionalA=Optional.of(a);

        //创建指定引用的 Optional 实例，若引用为 null 则表示缺失
        Optional<Integer> optionalB = Optional.fromNullable(a);
        System.out.println(optionalB.isPresent());

        //java.lang.IllegalStateException: Optional.get() cannot be called on an absent value
        //optionalB.get();


        //如果optionalB的引用缺失 则返回设定的默认值
        Integer or = optionalB.or(100);
        System.out.println(or);

        //将optional的引用转换为set集合
        Set<Integer> integers = optionalB.asSet();
    }
}
