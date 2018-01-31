package com.aqs.pool;

import java.util.concurrent.*;

/**
 * Created by shsun on 1/14/18.
 */
public class ThreadDeadLock {
    ExecutorService exec = Executors.newSingleThreadExecutor();

    public class RendererPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;

            header = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("加载页眉");
                    Thread.sleep(2 * 1000);
                    return "页眉";
                }
            });

            footer = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("加载页脚");
                    Thread.sleep(3 * 1000);
                    return "页脚";
                }
            });

            System.out.println("渲染页面主体");

            return header.get() + footer.get();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadDeadLock td = new ThreadDeadLock();
        Future<String> futre = td.exec.submit(td.new RendererPageTask());
        String result = futre.get();
        System.out.println("执行结果为：" + result);

    }

}
