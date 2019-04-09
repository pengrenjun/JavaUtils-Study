package com.guavaStudy.guavaConcurent;

import com.google.common.util.concurrent.*;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @Description Guava中并发ListenableFuture使用
 * @Date 2019/4/8 0008 下午 2:39
 * @Created by Pengrenjun
 */
public class ListenableFutureDemo {



    /**
     * ListenableFuture顾名思义就是可以监听的Future，它是对java原生Future的扩展增强。
     * 我们知道Future表示一个异步计算任务，当任务完成时可以得到计算结果。
     * 如果我们希望一旦计算完成就拿到结果展示给用户或者做另外的计算，就必须使用另一个线程不断的查询计算状态。
     * 这样做，代码复杂，而且效率低下。
     * 使用ListenableFuture Guava帮我们检测Future是否完成了，如果完成就自动调用回调函数，这样可以减少并发程序的复杂度。
     */
    @Test
    public void testListenableFuture(){

        //定义ListenableFuture的实例
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        Task task=new Task();
        //有了ListenableFuture实例，有两种方法可以执行此Future并执行Future完成之后的回调函数
        // ListenableFuture要做的工作，在Callable接口的实现类中定义
        ListenableFuture<String> listenableFuture = executorService.submit(task);
        //方法一：通过ListenableFuture的addListener方法
       /* listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + listenableFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);*/

        //方法二：通过Futures的静态方法addCallback给ListenableFuture添加回调函数
        //推荐使用第二种方法，因为第二种方法可以直接得到Future的返回值，或者处理错误情况。
        //本质上第二种方法是通过调动第一种方法实现的，做了进一步的封装。
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {

            @Override
            public void onSuccess(@Nullable String result) {

                System.out.println("任务执行成功后的回调处理");
                System.out.println("执行结果的返回值:"+result);

            }
            @Override
            public void onFailure(Throwable t) {
            }
        },executorService);
    }

    class Task implements Callable<String>{

        @Override
        public String call() throws Exception {

            System.out.println("开始执行线程任务");


            return "任务执行OK!";
        }
    }

    /**
     * 另外ListenableFuture还有其他几种内置实现：
     * SettableFuture：不需要实现一个方法来计算返回值，而只需要返回一个固定值来做为返回值，可以通过程序设置此Future的返回值或者异常信息
     * CheckedFuture： 这是一个继承自ListenableFuture接口，他提供了checkedGet()方法，此方法在Future执行发生异常时，可以抛出指定类型的异常。
     */


    /**
     * 使用场景是这样的，客户端需要上传数据到服务端，涉及到几个表的插入。
     * 之前都是一个一个表插入的，性能不是很好的。现在改为并发插入
     */
    @Test
    public void testB(){

         //并发任务执行器
         ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));

         //并发任务处理结果集
         List<ListenableFuture<?>> listenableFutures = new ArrayList<>();

        //保存账单信息执行线程
        listenableFutures.add(executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("保存账单信息任务执行！"+Thread.currentThread());
                }
            }));

        //保存订单信息执行线程
        listenableFutures.add(executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存订单信息任务执行！"+Thread.currentThread());
            }
        }));

        //保存支付流水执行线程
        listenableFutures.add(executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("保存支付流水执行任务执行！"+Thread.currentThread());
            }
        }));

        String result = null;
        ListenableFuture<List<Object>> results = Futures.allAsList(listenableFutures);
        try {
            for (Object obj : results.get()) {
                if (obj != null) {
                    result = obj.toString();
                    System.out.println("处理result:"+result);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
