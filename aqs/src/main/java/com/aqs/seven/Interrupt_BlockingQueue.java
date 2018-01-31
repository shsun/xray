package com.aqs.seven;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Interrupt_BlockingQueue {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Worker());
        t.start();

        Thread.sleep(200);
        t.interrupt();

        System.out.println("Main thread stopped.");
    }

    public static class Worker implements Runnable {
        final BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(10);

        public void run() {
            System.out.println("Worker started.");

            BigInteger p = BigInteger.ONE;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("1 Worker IsInterrupted: " + Thread.currentThread().isInterrupted() + ", " + e.getMessage());
            }

            while (!Thread.currentThread().isInterrupted()) {
                p = p.nextProbablePrime();
                try {
                    queue.put(p);
                } catch (InterruptedException e) {
                    System.out.println("2 Worker IsInterrupted: " + Thread.currentThread().isInterrupted() + ", " + e.getMessage());
                }
            }

            System.out.println("Worker stopped. " + Thread.currentThread().isInterrupted());
        }
    }
}
