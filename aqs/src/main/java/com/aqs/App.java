package com.aqs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        AtomicInteger integer = new AtomicInteger();
        integer.incrementAndGet();

        AtomicReference<Integer> reference = new AtomicReference<Integer>();
        reference.set(new Integer(1));

        System.out.println("Hello World!");

        for (int i = 0; i < 100; i++) {
            NoVisibility.a();
        }

    }
}
