package com.guavaStudy.guavaConcurent;

import com.google.common.util.concurrent.AbstractIdleService;

/**
 * @Description 这个提供的service主要是负责一些服务器的启动等。比如一些netty,redis等启动关闭的可以放在里面管理
 * @Date 2019/4/10 0010 下午 1:51
 * @Created by Pengrenjun
 */
public class AbstractIdleServiceDemo extends AbstractIdleService {

    /**
     *  AbstractIdleService 类简单实现了Service接口、其在running状态时不会执行任何动作
     *  因此在running时也不需要启动线程–但需要处理开启/关闭动作。
     *  要实现一个此类的服务，只需继承AbstractIdleService类，然后自己实现startUp() 和shutDown()方法就可以了
     */
    @Override
    protected void startUp() throws Exception {

        Thread.sleep(2000);
        System.out.println("Netty服务启动"+Thread.currentThread().toString());

    }

    @Override
    protected void shutDown() throws Exception {

        System.out.println("Netty服务启动关闭"+Thread.currentThread().toString());

    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {



        AbstractIdleServiceDemo abstractIdleServiceDemo=new AbstractIdleServiceDemo();

        try {

            System.out.println(abstractIdleServiceDemo.state());

            abstractIdleServiceDemo.startAsync().awaitRunning();

            System.out.println(abstractIdleServiceDemo.state());

            System.out.println("Netty服务已启动了！");
            abstractIdleServiceDemo.shutDown();

            System.out.println(abstractIdleServiceDemo.state());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
