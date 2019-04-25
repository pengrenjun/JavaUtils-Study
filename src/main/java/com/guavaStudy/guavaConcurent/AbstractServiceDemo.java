package com.guavaStudy.guavaConcurent;

import com.google.common.util.concurrent.AbstractService;
import org.checkerframework.checker.units.qual.A;

/**
 * @Description 如需要自定义的线程管理、可以通过扩展 AbstractService类来实现
 * @Date 2019/4/25 0025 上午 11:17
 * @Created by Pengrenjun
 */
public class AbstractServiceDemo {

//    一般情况下、使用上面的几个实现类就已经满足需求了，但如果在服务执行过程中有一些特定的线程处理需求、则建议继承AbstractService类。
//    继承AbstractService方法必须实现两个方法.
//    doStart():  首次调用startAsync()时会同时调用doStart(),
//    doStart()内部需要处理所有的初始化工作、如果启动成功则调用notifyStarted()方法；启动失败则调用notifyFailed()

//    doStop():  首次调用stopAsync()会同时调用doStop(),doStop()要做的事情就是停止服务，
//    如果停止成功则调用 notifyStopped()方法；停止失败则调用 notifyFailed()方法。
//    doStart和doStop方法的实现需要考虑下性能，尽可能的低延迟。如果初始化的开销较大，
//    如读文件，打开网络连接，或者其他任何可能引起阻塞的操作，建议移到另外一个单独的线程去处理


    /**
     * 自定义的AbstractService用来对线程执行进行特殊的处理
     */
    static class CustomerAbstractService extends AbstractService{



        @Override
        protected void doStart() {

            System.out.println("Job 准备开始执行");
            try {
                //需要处理所有的初始化工作 如读文件，打开网络连接，或者其他任何可能引起阻塞的操作，建议移到另外一个单独的线程去处理
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Implementing classes should invoke this method once their service has started.
            // It will cause the service to transition from {@link State#STARTING} to {@link State#RUNNING}.
            notifyStarted();
        }

        @Override
        protected void doStop() {
            System.out.println("Job 执行完毕OK!");
            //Implementing classes should invoke this method once their service has stopped. It will cause
            // the service to transition from {@link State#STARTING} or {@link State#STOPPING} to {@link
            // State#TERMINATED}.
            notifyStopped();
        }
    }


    public static void main(String[] args) {

        CustomerAbstractService customerAbstractService=new CustomerAbstractService();

        customerAbstractService.startAsync();

        customerAbstractService.stopAsync();


    }
}
