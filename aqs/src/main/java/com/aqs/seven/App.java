package com.aqs.seven;

import java.math.BigInteger;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        App app = new App();
        try {
            app.aSecondOfPrimes();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("static-access")
	List<BigInteger> aSecondOfPrimes() throws InterruptedException {

        // PrimeGenerator_List_CancleByFlag generator = new PrimeGenerator_List_CancleByFlag();
        // PrimeGenerator_BlockingQueue_CancleByFlag generator = new PrimeGenerator_BlockingQueue_CancleByFlag();

        PrimeGenerator_BlockingQueue_CancleByInterrupt generator = new PrimeGenerator_BlockingQueue_CancleByInterrupt();

        new Thread(generator).start();

        try {
            Thread.currentThread().sleep(10 * 2);
        } finally {
           // abc();
            System.out.println(".....cancel");

            generator.cancel();

            //System.out.println(" before=" + generator.isAlive() + ", i=" + generator.isInterrupted());
            //Thread.currentThread().sleep(4);
            //abc();
            //System.out.println(" after=" + generator.isAlive() + ", i=" + generator.isInterrupted());
        }
        List<BigInteger> r = generator.get();

        return r;
    }

    private void abc() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("\n\nThread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getName());
        }
        System.out.println("");

    }

}
