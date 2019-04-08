package com.guavaStudy.guavaCollection;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.junit.Test;

/**
 * Created by Administrator on 2019/4/5.
 */
public class ClassToInstanceMapDemo {

    @Test
    public void testClassToInstanceMap(){

        ClassToInstanceMap classToInstanceMap= MutableClassToInstanceMap.create();

        //为了扩展 Map 接口，ClassToInstanceMap 额外声明了两个方法：
        // T getInstance(Class) 和 T putInstance(Class, T)，从而避免强制类型转换，同时保证了类型安全
        classToInstanceMap.putInstance(Integer.class,123);
        classToInstanceMap.putInstance(Integer.class,234);

        classToInstanceMap.putInstance(String.class,"123456");
        classToInstanceMap.putInstance(String.class,"qweasd");

        System.out.println(classToInstanceMap.toString());

        System.out.println(classToInstanceMap.size());



    }
}
