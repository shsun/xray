package com.aqs.pool;

/**
 * Created by shsun on 1/13/18.
 */
public class ABC extends Object {

    public int i = 1;

    public boolean sleep;

    public ABC(Boolean pSleep) {

        super();

        if (pSleep) {
            try {
                Thread.currentThread().sleep(1000L);
            } catch (Throwable t) {
            }
        }

        i = 2;
        this.sleep = pSleep;

        System.out.println("c i=" + i + ", sleep=" + pSleep + ", " + Thread.currentThread().getName());
    }


    public void sayhi(){
        System.out.println("hi i=" + i + ", sleep=" + this.sleep + ", " + Thread.currentThread().getName());

    }

}
