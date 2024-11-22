package com.cszczotka.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * https://javarevisited.blogspot.com/2013/03/reentrantlock-example-in-java-synchronized-difference-vs-lock.html
 */
public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public int getCount() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
            return count++;
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getCountTwo() {
        return count++;
    }


    public static void main(String args[]) {
        final ReentrantLockDemo counter = new ReentrantLockDemo();

        Thread t1 = new Thread(() -> {
            while (counter.getCount() < 6) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();                    }
            }
        });

        Thread t2 = new Thread(() -> {
            while (counter.getCount() < 6) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}

