package com.guavaStudy.guavuStringHandlers;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.reflect.TypeToken;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description guava对字符串处理常用方法测试
 * @Date 2019/4/25 0025 下午 2:45
 * @Created by Pengrenjun
 */
public class BaseFunctionDemo {

    //joiner 实例总是不可变的。用来定义 joiner 目标语义的配置方法总会返回一个新的 joiner 实例。
    // 这使得 joiner 实例都是线程安全的，你可以将其定义为 static final常量
    private static final Joiner joiner=Joiner.on("; ").skipNulls();

    //连接器[Joiner]
    @Test
    public void testA(){

        String join = joiner.join("Harry", null, "Ron", "Hermione");

        System.out.println(join);

        String join1 = Joiner.on(",").join(Arrays.asList(1, 5, 7));

        System.out.println(join1);

    }

    //拆分器[Splitter]
    @Test
    public void testB(){
        String str=",a,,b,";

        String[] split = str.split(",");

       for(String a:split){
           System.out.println(a);
       }

        //Splitter 使用令人放心的、直白的流畅 API 模式对这些混乱的特性作了完全的掌控
        //Splitter 可以被设置为按照任何模式、字符、字符串或字符匹配器拆分
        Iterable<String> split1 = Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,, qux");

        for(String w:split1){
            System.out.println(w);
        }

    }






    @Test
    public void testC(){

        String theDigits = CharMatcher.inRange('0','9').retainFrom("123qwe"); //只保留数字字符
        System.out.println(theDigits);


        //去除两端的空格，并把中间的连续空格替换成单个
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(" qw   adsf  ", ' ');
        System.out.println(spaced);

        //用*号替换所有
        String noDigits = CharMatcher.inRange('0','9').replaceFrom("12aadf", "*");
        System.out.println(noDigits);
       // 只保留数字和小写字母
        String lowerAndDigit = CharMatcher.inRange('0','9').or(CharMatcher.inRange('a', 'z')).retainFrom("QWE 123 asd");
        System.out.println(lowerAndDigit);



    }

    @Test
    public void testD(){
        String constant_name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");// returns "constantName

        System.out.println(constant_name);
    }
}
