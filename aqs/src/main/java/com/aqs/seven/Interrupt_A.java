package com.aqs.seven;

/**
 * Created by shsun on 1/9/18.
 */
public class Interrupt_A {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Worker());
        t.start();

        Thread.sleep(200);
        t.interrupt();

        System.out.println("Main thread stopped.");
    }

    public static class Worker implements Runnable {
        public void run() {
            System.out.println("Worker started.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread curr = Thread.currentThread(); // 再次调用interrupt方法中断自己，将中断状态设置为“中断”
                System.out.println("\n---------before interrupt()----------");
                System.out.println("Worker IsInterrupted: " + curr.isInterrupted());
                System.out.println("Worker IsInterrupted: " + curr.isInterrupted());

                System.out.println("\n---------after interrupt()----------");

                curr.interrupt();
                System.out.println("Worker IsInterrupted: " + curr.isInterrupted());
                System.out.println("Worker IsInterrupted: " + curr.isInterrupted());

                System.out.println("\nStatic Call: " + curr.isInterrupted() + ", " + Thread.interrupted() + ", " + curr.isInterrupted());// clear status
                System.out.println("---------After Interrupt_A Status Cleared----------");
                System.out.println("Static Call: " + Thread.interrupted());

                System.out.println("\nWorker IsInterrupted: " + curr.isInterrupted());
                System.out.println("Worker IsInterrupted: " + curr.isInterrupted());
            }

            System.out.println("\nWorker stopped.");
        }
    }
}