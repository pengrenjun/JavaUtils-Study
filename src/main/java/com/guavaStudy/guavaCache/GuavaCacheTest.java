package com.guavaStudy.guavaCache;

import guavaCache.GuavaCache.AbstractGuavaCache;
import guavaCache.GuavaCache.DefaultGuavaCacheManage;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2019/4/6.
 */
public class GuavaCacheTest {

    @Test
    public void testA() throws ExecutionException {

        GuavaCacheUtils guavaCacheUtils = GuavaCacheUtils.create();
        guavaCacheUtils.put(1,2);

        Object o = guavaCacheUtils.get(1);
        System.out.println(o);

        System.out.println(guavaCacheUtils.getUnchecked(2));


    }


}
