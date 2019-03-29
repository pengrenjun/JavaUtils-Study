package com.guavaStudy;


import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.opslab.util.CheckUtil;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2019/3/29 0029 下午 3:34
 * @Created by Pengrenjun
 */
public class optionDemo {

    public static void main(String[] args) {

        List<Integer>  list= new ArrayList<>();

        Optional<List<Integer>> of = Optional.of(list);

        List<Integer> list1 = of.get();

        System.out.println(of.isPresent());



    }
}
