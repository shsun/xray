package com.aqs.pool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    /**
     * newCachedThreadPool
     * 
     * @throws InterruptedException @
     */
    public static void newCachedThreadPoolTest() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // try {
            // Thread.sleep(index * 100);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("current Thread name is " + Thread.currentThread().getName() + " index: " + index);
                }
            });
        }
        Thread.sleep(200);
        // cachedThreadPool.shutdown();//.awaitTermination(10, TimeUnit.SECONDS); //这个函数功能存疑
        System.out.println("cachedThreadPool isShutdown() is : " + cachedThreadPool.isShutdown()); // 上行不注释，则结果为false，上行不注释，则结果为true
        System.out.println("cachedThreadPool isTerminated() is : " + cachedThreadPool.isTerminated());// 上上行注释不注释，结果均为false

        cachedThreadPool.execute(new Runnable() { // cachedThreadPool shutdown 之后，这个execute() 不能之前且会报异常
            @Override
            public void run() {
                System.out.println(
                        "cachedThreadPool is not shutdown and this task can be executing. " + "\n current Thread name is " + Thread.currentThread().getName());
            }
        });
    }

    /**
     * newFixedThreadPool
     */
    public static void fixedThreadPoolTest() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); // 这个函数返回的是 ThreadPoolExecutor
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("current Thread name is " + Thread.currentThread().getName() + " index : " + index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("10个任务已分发完毕"); // 这句大概率性优先于run 里面的print 语句先执行
    }

    /**
     * 产生一个ScheduledExecutorService对象， newScheduledThreadPool(3),这个对象的线程池大小为3，若任务数量大于3， 任务会在一个queue里等待执行 scheduleWithFixedDelay 函数：第一个参数new Runnable 就是任务
     * 所谓线程池，就是能接受任务。线程池的好处是帮你调度线程，不然还得自己写调度多个线程的方法，比如周期性执行任务
     */
    public static void scheduledThreadPoolTest() {
        final int initialDelay = 1;
        final int delay = 2;

        System.out.println("---------------------");

        // ScheduledExecutorService 只是个接口，ScheduledThreadPoolExecutor 才是实现此接口的类.ScheduledThreadPoolExecutor 还继承了ThreadPoolExecutor 线程池类
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3); // 这个函数返回的是ScheduledThreadPoolExecutor

        for (int i = 0; i < 50; i++) {
            final int index = i;
            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() { // scheduleWithFixedDelay 是周期性的,
                @Override // scheduleWithFixedDelay()方法不是ThreadPool 类的是ScheduledExecutorService 接口特有的
                public void run() {
                    try {
                        Thread.sleep(1900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(index + " Thread name is " + Thread.currentThread().getName() + " time is : " + new Date().toString());
                }
            }, initialDelay, delay, TimeUnit.SECONDS);
        }

    }

    /**
     * 单线程线程池，保证所有任务都按照指定顺序(FIFO, LIFO, 优先级)执行
     */
    public static void singleThreadExecutorTest() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(
                                "current Thread name is " + Thread.currentThread().getName() + " index : " + index + " time is : " + new Date().toString());
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * (5)
     */
    public static void SingleThreadScheduledExecutorTest() {
        ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
        System.out.println("---------------------");

        final int initialDelay = 5;
        final int delay = 1;
        scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
            // scheduleWithFixedDelay 是周期性的
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("current Thread name is " + Thread.currentThread().getName() + " time is : " + new Date().toString());
            }
        }, initialDelay, delay, TimeUnit.SECONDS);
        // 第二个参数1，虽然是1秒为周期，但是单线程线程池，如果上一个任务没执行完，那么会等2秒或者更多，
    }
    // 直到上一个任务执行完，才执行下一个任务。即执行完这个条件优先最高，频率次之

    public static void main(String[] args) throws InterruptedException {
        //newCachedThreadPoolTest();
        // fixedThreadPoolTest();
        scheduledThreadPoolTest();
        // singleThreadExecutorTest();
        // SingleThreadScheduledExecutorTest();
    }
}
