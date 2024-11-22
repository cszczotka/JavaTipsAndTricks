package com.cszczotka.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    private int count = 0;

    public void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "writing count: " + count);
            count++;
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "wrote count: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }
    public int read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "read count: " + count);
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String args[]) {
        final ReentrantReadWriteLockDemo counter = new ReentrantReadWriteLockDemo();

        Thread t1 = new Thread(() -> {
            while (counter.read() < 6) {
                try {
                    Thread.sleep(1000);
                    counter.write();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();                    }
            }
        });

        Thread t2 = new Thread(() -> {
            while (counter.read() < 6) {
                try {
                    Thread.sleep(1000);
                    counter.write();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
