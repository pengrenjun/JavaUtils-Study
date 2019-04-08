package com.guavaStudy.guavaCache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 传统版缓存工具类
 * Created by Administrator on 2019/4/6.
 */
public class GuavaCacheUtils<K,V> {

    /**
     * 创建缓存对象
     * @return
     */
    public static GuavaCacheUtils create() {
        return  new GuavaCacheUtils();
    }
    private LoadingCache<K, V> Cache;

    /**
     * 无参构造器自动创建缓存
     */
    private GuavaCacheUtils() {
        Cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                // 缓存刷新时间
                .refreshAfterWrite(10, TimeUnit.MINUTES)
                // 设置缓存个数
                .maximumSize(500)
                .build(new CacheLoader<K, V>() {
                    @Override
                    // 当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                    public V load(K appKey) {
                        return getEntryFromDB(appKey);
                    }

                    // 数据库进行查询
                    private V getEntryFromDB(K name) {

                       return (V)"123";
                    }
                });
        cleanCache();
    }

    /**
     * 清缓存
     */
    public void cleanCache() {
        Cache.cleanUp();
    }

    /**
     * 取值
     * @param key
     * @param callable
     * @return
     * @throws ExecutionException
     */
    public V get(K key, Callable<V> callable) throws ExecutionException {
        return Cache.get(key, callable);
    }

    public V get(K key) throws ExecutionException {
        return Cache.get(key);
    }

    public V getUnchecked(K key){
        return Cache.getUnchecked(key);
    }


    /**
     * 存入缓存的数据
     * @param key
     * @param value
     * @throws ExecutionException
     */
    public void put(K key, V value) throws ExecutionException {
        Cache.put(key, value);
    }
}
