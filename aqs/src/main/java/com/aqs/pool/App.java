package com.aqs.pool;

import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

        final int corePoolSize = 2;
        final int maximumPoolSize = 3;
        final long keepAliveTime = 100L;

        final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(3);
        try {
            ;
            //workQueue.put(new XUTask(null, null));
            //workQueue.put(new XUTask(null, null));
            //workQueue.put(new XUTask(null, null));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //final ThreadFactory threadFactory = Executors.defaultThreadFactory();
        final ThreadFactory threadFactory = Executors.privilegedThreadFactory();

        //final RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        // final RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
        // final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        final RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

        final ThreadPoolExecutor poolExecutor_1 =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory, handler);

        final ThreadPoolExecutor poolExecutor =
                new TimingThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory, handler);


        for (int i = 0; i < 2; i++) {
            final int index = i;
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ;
                        //Thread.sleep(1000 * 5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("\n" + index + ", thread_name=" + Thread.currentThread().getName() + ", queue.size=" + poolExecutor.getQueue().size());
                }
            });
        }

        try {
            Thread.sleep(1000 * 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("go hell");

        System.out.println("go hell 2");

    }
}
