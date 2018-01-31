package com.aqs.seven;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by shsun on 1/9/18.
 */
public class PrimeGenerator_BlockingQueue_CancleByInterrupt extends Thread implements Runnable {

    private final BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(1000);
    private volatile boolean canceled = false;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!isInterrupted()) {
            System.out.print("A-");
            p = p.nextProbablePrime();
            try {
                primes.put(p);
                System.out.print("-put done-");
            } catch (InterruptedException e) {
                System.err.println("Err-" + e.getMessage());
            }
            System.out.println("B-" + p.toString() + "--" + primes.size());
        }
        System.out.println("Run-done");
    }

    public void cancel() {
        super.interrupt();
        // primes.clear();
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

}
