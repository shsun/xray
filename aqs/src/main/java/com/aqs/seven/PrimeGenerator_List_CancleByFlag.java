package com.aqs.seven;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shsun on 1/9/18.
 */
public class PrimeGenerator_List_CancleByFlag extends Thread implements Runnable {

    private final List<BigInteger> primes = new ArrayList<BigInteger>();

    private volatile boolean canceled = false;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!canceled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
                System.out.println(p.toString() + "\n");
            }
        }
    }


    public List<BigInteger> list() {
        return primes;
    }


    public void cancel() {
        canceled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

}
