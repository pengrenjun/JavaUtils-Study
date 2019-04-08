package com.guavaStudy.guavaCache.GuavaCache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/4/6.
 */
public abstract class AbstractGuavaCache<K, V> {

   // private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGuavaCache.class);

    /**
     * loadingCache 核心实例
     */
    private volatile LoadingCache<K, V> loadingCache;

    /**
     * 并发级别
     */
    private int concurrencyLevel;

    /**
     * 缓存超时时间
     */
    private long expireTime;

    /**
     * 初始容量
     */
    private int initialCapacity;

    /**
     * 最大容量
     */
    private long maxSize;



    private  ListeningExecutorService backgroundRefreshPools ;


    protected AbstractGuavaCache(int concurrencyLevel, long expireTime, int initialCapacity, long maxSize) {
        this.concurrencyLevel = concurrencyLevel;
        this.expireTime = expireTime;
        this.initialCapacity = initialCapacity;
        this.maxSize = maxSize;

            backgroundRefreshPools= MoreExecutors.listeningDecorator(new ThreadPoolExecutor(10, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>()));



    }

    protected AbstractGuavaCache(){

    }

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉
     *
     * @Param
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    public LoadingCache<K, V> getCache() {
        // 双重检查锁-防止重复加载
        if (loadingCache == null) {
            synchronized (this) {
                if (loadingCache == null) {
                    loadingCache = CacheBuilder.newBuilder()
                            // 设置并发级别为8
                            .concurrencyLevel(concurrencyLevel)
                            // 设置多久不读缓存废弃
                            .expireAfterAccess(expireTime, TimeUnit.HOURS)
                            // 设置初始条数
                            .initialCapacity(initialCapacity)
                            // 设置最大条数,超过则按照LRU策略淘汰缓存
                            .maximumSize(maxSize)
                            // 设置要统计缓存的命中率
                            .recordStats()
                            // 设置缓存的移除通知
                            .removalListener(removalNotification -> {
                                /*if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("{} was moved,cause is {}", removalNotification.getKey(),
                                            removalNotification.getCause());
                                }*/
                            }).build(
                                    // 当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                                    new CacheLoader<K, V>() {
                                        @Override
                                        public V load(K k) throws Exception {

                                            return fetchData(k);
                                        }

                                        @Override
                                        /**
                                         * 后台线程刷新:https://www.cnblogs.com/csonezp/p/10011031.html
                                         就像上面所说，只要刷新缓存，就必然有线程被阻塞，这个是无法避免的。

                                         虽然无法避免线程阻塞，但是我们可以避免阻塞用户线程，让用户无感知即可。

                                         所以，我们可以把刷新线程放到后台执行。当key过期时，有新用户线程读取cache时，开启一个新线程去load DB的数据，用户线程直接返回老的值，这样就解决了这个问题。
                                         */
                                        // 刷新时，开启一个新线程异步刷新，老请求直接返回旧值，防止耗时过长
                                        public ListenableFuture<V> reload(K key, V oldValue) throws Exception {
                                            return backgroundRefreshPools.submit(() -> fetchData(key));
                                        }
                                    });
                }
            }
        }
       /* if (LOGGER.isInfoEnabled()) {
            LOGGER.info("本地缓存{}初始化成功", this.getClass().getSimpleName());
        }*/
        return loadingCache;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 〈功能详细描述〉载入数据
     *
     * @Param
     * @return
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     **/
    protected abstract V fetchData(K k);

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
