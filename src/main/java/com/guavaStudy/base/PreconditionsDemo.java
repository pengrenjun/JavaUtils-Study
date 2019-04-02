package com.guavaStudy.base;

import com.google.common.base.Preconditions;
import com.opslab.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @Description 前置条件：让方法调用的前置条件判断更简单
 * @Date 2019/4/2 0002 下午 2:25
 * @Created by Pengrenjun
 */
public class PreconditionsDemo {

    /**
     * 相比 Apache Commons 提供的类似方法，我们把 Guava 中的 Preconditions 作为首选
     *
     * • 在静态导入后，Guava 方法非常清楚明晰。checkNotNull 清楚地描述做了什么，会抛出什么异常；
     * • checkNotNull 直接返回检查的参数，让你可以在构造函数中保持字段的单行赋值风格：this.field = check
     * NotNull(field)
     * • 简单的、参数可变的 printf 风格异常信息。鉴于这个优点，在 JDK7 已经引入 Objects.requireNonNull 的
     * 情况下，我们仍然建议你使用 checkNotNull。
     *
     * 在编码时，如果某个值有多重的前置条件，我们建议你把它们放到不同的行，这样有助于在调试时定位。此
     * 外，把每个前置条件放到不同的行，也可以帮助你编写清晰和有用的错误消息。
     * @param args
     */

    public static void main(String[] args) {

        test();
    }

    private static void test() {

        String str="qwe";

        //检查参数
       // Preconditions.checkArgument(str.equals("qweqwe"));
        try {
            Preconditions.checkArgument(str.equals("qweqwe")," Argument was %s but %s expected nonnegative", str,"qweqwe");
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Integer> list=null;
        //检测非空
        List<Integer> list1 = Preconditions.checkNotNull(list);


    }
}
