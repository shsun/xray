package com.aqs;

import java.util.concurrent.*;

/**
 * Created by shsun on 1/seven/18.
 */
public class CallableAndFuture {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        for (int i = 1; i < 5; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(10000-taskID * 2000);
                    return taskID;
                }
            });
        }
        for (int i = 1; i < 5; i++) {
            try {
                System.out.println(i + "");
                System.out.println(i + "------->" + cs.take().get());
                System.out.println("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
