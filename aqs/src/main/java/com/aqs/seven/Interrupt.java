package com.aqs.seven;

public class Interrupt {
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Worker IsInterrupted: " + Thread.currentThread().isInterrupted() + ", " + e.getMessage());
            }

            System.out.println("Worker stopped. " + Thread.currentThread().isInterrupted());
        }
    }
}
