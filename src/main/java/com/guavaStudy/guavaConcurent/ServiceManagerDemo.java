package com.guavaStudy.guavaConcurent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import org.omg.CORBA.ARG_OUT;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description Guava还提供了 ServiceManager类使得涉及到多个Service集合的操作更加容易。
 * 通过实例化ServiceManager类来创建一个Service集合
 * @Date 2019/4/25 0025 下午 1:50
 * @Created by Pengrenjun
 */
public class ServiceManagerDemo {

    /**
     * 你可以通过以下方法来管理它们：
     * startAsync()  ： 将启动所有被管理的服务。如果当前服务的状态都是NEW的话、那么你只能调用该方法一次、这跟 Service#startAsync()是一样的。
     * stopAsync()：将停止所有被管理的服务。
     * addListener：会添加一个ServiceManager.Listener，在服务状态转换中会调用该Listener
     * awaitHealthy()：会等待所有的服务达到Running状态
     * awaitStopped()：会等待所有服务达到终止状态
     * 检测类的方法有：
     * isHealthy()  ：如果所有的服务处于Running状态、会返回True
     * servicesByState()：以状态为索引返回当前所有服务的快照
     * startupTimes() ：返回一个Map对象，记录被管理的服务启动的耗时、以毫秒为单位，同时Map默认按启动时间排序。
     * 我们建议整个服务的生命周期都能通过ServiceManager来管理，不过即使状态转换是通过其他机制触发的、也不影响ServiceManager方法的正确执行。
     * 例如：当一个服务不是通过startAsync()、而是其他机制启动时，listeners 仍然可以被正常调用、awaitHealthy()也能够正常工作。
     * ServiceManager 唯一强制的要求是当其被创建时所有的服务必须处于New状态
     */

     static class ConsumerServiceManager  {

       private   ImmutableList.Builder<Service> services = ImmutableList.builder();

       private   ServiceManager serviceManager;

       public ServiceManager addServices(Service ... service){
           services.add(service);
           serviceManager=new ServiceManager(services.build());
           return serviceManager;
       }

       public ServiceManager  addListener(ServiceManager.Listener listener){

           Preconditions.checkNotNull(serviceManager);

           serviceManager.addListener(listener);
           return serviceManager;
       }

       //异步启动服务
       public void  startServices() throws TimeoutException {
           //等待所有的服务启动完毕
           serviceManager.startAsync().awaitHealthy(1, TimeUnit.MINUTES);
       }

       public void stopServices() throws TimeoutException {

           serviceManager.stopAsync().awaitStopped(1, TimeUnit.MINUTES);
       }
    }

    public static void main(String[] args) throws TimeoutException {

         Service service1=new AbstractScheduledServiceDemo.SchedulerJob();
         Service service2=new AbstractScheduledServiceDemo.SchedulerJob();
         Service service3=new AbstractScheduledServiceDemo.SchedulerJob();

        ConsumerServiceManager consumerServiceManager=new ConsumerServiceManager();

        ServiceManager serviceManager = consumerServiceManager.addServices(service1, service2, service3);

        consumerServiceManager.addListener(new ServiceManager.Listener() {
            @Override
            public void healthy() {
                System.out.println("》》》》》》》》》》启动监听》》》》》》》》》》》》》》");
            }

            @Override
            public void stopped() {
                System.out.println("》》》》》》》》》》执行完毕监听》》》》》》》》》》》》");
            }
            @Override
            public void failure(Service service) {
                System.out.println("？？？？？？？？？？？？？？？？？？？？");
            }
        });

        consumerServiceManager.startServices();

        System.out.println(">>>>>>>>>>>"+serviceManager.isHealthy());

        System.out.println(">>>>>>>>>>>"+serviceManager.startupTimes().toString());
    }

}
