package com.guavaStudy.guavaConcurent;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;


/**
 * @Description AbstractExecutionThreadService 通过单线程处理启动、运行、和关闭等操作。
 * 你必须重载run()方法，同时需要能响应停止服务的请求。
 * @Date 2019/4/12 0012 下午 2:23
 * @Created by Pengrenjun
 */
public class AbstractExecutionThreadServiceDemo {


    public static void main(String[] args) throws InterruptedException {

        System.out.println("主线程"+Thread.currentThread().toString());

        //异步开启服务线程
        AdbShellProcess adbShellProcess=new AdbShellProcess(new AndroidDevice());

        System.out.println("主线程已启动！");

        Thread.sleep(3000);

        adbShellProcess.shutDownAdbShellProcess();
    }



    static class AdbShellProcess{

        private AndroidDevice service;

        public AdbShellProcess(AndroidDevice service) {
            this.service = service;

            //服务添加监听器
            service.addListener(new Service.Listener() {
                @Override
                public void failed(final Service.State from, final Throwable failure) {
                    AdbShellProcess.this.onFail(failure);
                }
            }, MoreExecutors.newDirectExecutorService());

            service.startAsync();

        }

        private void onFail(Throwable failure) {
            System.out.println("启动AndroidDevice出现异常："+failure.toString());
        }


        /**
         * 关闭进程
         */
        public void shutDownAdbShellProcess(){
            System.out.println("准备关闭开启的AndroidDevice");

            //service.stopAsync();


            service.triggerShutdown();
            System.out.println("AndroidDevice已关闭！");


        }
    }


    /**
     *创建一个线程、然后在线程内调用run()方法\
     *
     * 注意：AbstractExecutionThreadService 通过单线程处理启动、运行、和关闭等操作。
     * 你必须重载run()方法，同时需要能响应停止服务的请求。具体的实现可以在一个循环内做处理
     */
    static class AndroidDevice extends  AbstractExecutionThreadService {

        private  boolean bl=true;



        @Override
        protected void run() throws Exception {

            System.out.println("安卓服务进程"+Thread.currentThread().toString()+">>安卓服务进程启动");

            while (isRunning()){

                int q=0;

                while(bl){

                    Thread.sleep(1000);

                    System.out.println(q++);
                }
            }
        }

        @Override
        protected void triggerShutdown() {
            bl=false;
        }
    }

}



