package com.aqs;

/**
 * Created by shsun on 1/6/18.
 */
public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {

        public void run() {

            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);

        }

    }

    public static void a() {
        new ReaderThread().start();
        ready = true;
        number = 42;
    }
}
