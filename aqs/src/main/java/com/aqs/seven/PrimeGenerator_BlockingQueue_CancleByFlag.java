package com.aqs.seven;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by shsun on 1/9/18.
 */
public class PrimeGenerator_BlockingQueue_CancleByFlag extends Thread implements Runnable {

    private final BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(10);
    private volatile boolean canceled = false;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!canceled) {
            System.out.print("A-");
            p = p.nextProbablePrime();
            try {
                // FIXME: 1/9/18 the loop won't be break for the blocking queue.
                // primes.put may hang current thread when the BlockingQueue is full.
                primes.put(p);
            } catch (InterruptedException e) {
                System.err.println("Err-" + e.getMessage());
            }
            System.out.println("B-" + p.toString() + "\n");
        }
    }

    public void cancel() {
        canceled = true;

        primes.clear();
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

}
