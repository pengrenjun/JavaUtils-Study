package com.guavaStudy.guavaCache.GuavaCache;

import java.util.concurrent.*;

/**
 * spring中进行注册的Guava缓存实体bean
 * Created by Administrator on 2019/4/6.
 */
public class DefaultGuavaCacheManage {

//    public static final Logger LOGGER = LoggerFactory.getLogger(DefaultGuavaCacheManage.class);

    /**
     * 缓存包装
     */
    private static AbstractGuavaCache<String, Object> wrapper;

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉初始化缓存容器
     *
     * @Param
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    @SuppressWarnings("unchecked")
    public static boolean initGuaveCache() {
        wrapper = DefaultGuavaCache.getInstance();
        if (wrapper != null) {
            return true;
        } else {
//            LOGGER.error("Fail to init Guava Cache !");
            return false;
        }
    }

    /**
     * 存放缓存数据
     * @param key
     * @param value
     */
    public static void put(String key,Object value){
        wrapper.getCache().put(key,value);
    }

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉通过key获取缓存值
     *
     * @Param key 缓存key
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    public static Object get(String key) {
        Object object = null;
        try {
            object = wrapper.getCache().get(key);
        } catch (ExecutionException e) {
//            LOGGER.error("get Guava cache exception");
        }
        return object;
    }



    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉通过key获取缓存值,如果没有采取一定措施
     *
     * @Param key 缓存的key
     * @return callable 回调函数
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    public static Object getOrDo(String key, Callable callable) {
        Object object = null;
        try {
            wrapper.getCache().get(key, callable);
        } catch (ExecutionException e) {
//            LOGGER.error("getOrDo Guava cache exception");
        }
        return object;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉清除指定key缓存
     *
     * @Param key 缓存的key
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    public static void invalidate(String key) {
        wrapper.getCache().invalidate(key);
    }

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉清除所有缓存
     *
     * @Param
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    public static void inalidateAll(){
        wrapper.getCache().invalidateAll();
    }

    /**
     * 静态内部类实现单例
     *
     * @param <String>
     * @param <Object>
     */
    static class DefaultGuavaCache<String, Object> extends AbstractGuavaCache<String, Object> {

        private static AbstractGuavaCache guavaCache =new DefaultGuavaCache<>();

        public static AbstractGuavaCache getInstance() {
            return guavaCache;
        }

        private DefaultGuavaCache(int concurrencyLevel, long expireTime, int initialCapacity, long maxSize) {
            super(concurrencyLevel, expireTime, initialCapacity, maxSize);
        }

        private  DefaultGuavaCache(){
            super();
        }


        @Override
        protected Object fetchData(String s) {
            //具体操作
            return null;
        }
    }

}
