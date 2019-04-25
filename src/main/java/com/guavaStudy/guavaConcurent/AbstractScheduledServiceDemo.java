package com.guavaStudy.guavaConcurent;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description AbstractScheduledService类用于在运行时处理一些周期性的任务
 * @Date 2019/4/25 0025 上午 9:55
 * @Created by Pengrenjun
 */
public class AbstractScheduledServiceDemo {

/*
    子类可以实现 runOneIteration()方法定义一个周期执行的任务，以及相应的startUp()和shutDown()方法。
    为了能够描述执行周期，你需要实现scheduler()方法。
    通常情况下，你可以使用AbstractScheduledService.Scheduler类提供的两种调度器：
    newFixedRateSchedule(initialDelay, delay, TimeUnit)  和
    newFixedDelaySchedule(initialDelay, delay, TimeUnit)，
    类似于JDK并发包中ScheduledExecutorService类提供的两种调度方式
*/



//    Junit| 采用Junit测试时,注意用户的线程会自动停止
//    正常的程序运行时,JVM的停止是在所有用户线程(也就是非守护线程)运行完毕后才推出JVM,
//    但是如果是在JUnit测试的@Test方法中测试,程序正常运行完毕后用户线程会自动推出而不是一直的得带所有用户线程运行完毕
   /* @Test
    public void testA(){

       // DeptImportScheduledService deptImportScheduledService=new DeptImportScheduledService();
    }*/

    public static void main(String[] args) {

        SchedulerJob job=new SchedulerJob();

        //开启异步执行的定时任务
        Service service = job.startAsync();

    }


    static class SchedulerJob extends AbstractScheduledService {

        private List<String> list;

        private Boolean isEnd = false;

        @Override
        protected void runOneIteration() throws Exception {

            if(list.size()<10){
                System.out.println(this.hashCode()+"->Add element to list ..........");
                list.add("test");
            }
            else {
                System.out.println(this.hashCode()+"ShutDown job ..........");
                System.out.println(list.toString());
                isEnd = true;
                this.stopAsync();
            }
        }

        @Override
        protected void startUp() throws Exception {
            System.out.println(this.hashCode()+"Job start ..........");
            list = new ArrayList<>();
        }

        @Override
        protected void shutDown() throws Exception {
            System.out.println(this.hashCode()+"Job end ..........");
        }

        @Override
        protected Scheduler scheduler() {
            return Scheduler.newFixedRateSchedule(0,1, TimeUnit.SECONDS);
        }
    }
}
