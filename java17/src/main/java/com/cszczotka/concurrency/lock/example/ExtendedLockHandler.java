package com.cszczotka.concurrency.lock.example;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ExtendedLockHandler {

    private ReentrantLock lock = new ReentrantLock();
    private Map<Object, LockCounter> lockMap = new WeakHashMap<>();

    public boolean lock(Object[] elements) {
        var currentThread = getCurrentThread();
        if (lock.tryLock()) {
            for (Object element : elements) {
                if (lockMap.containsKey(element)) {
                    if (!lockMap.get(element).isSameThread(currentThread)) {
                        free();
                        return false;
                    }
                }
            }
            for (Object element : elements) {
                if (lockMap.containsKey(element)) {
                    lockMap.get(element).inc();
                } else
                    lockMap.put(element, new LockCounter(currentThread));
            }
            free();
            return true;
        }
        return false;
    }

    public boolean unlock(Object[] elements) {
        if (lock.tryLock()) {
            for (Object element : elements) {
                if (lockMap.containsKey(element)
                        && lockMap.get(element).isCurrentThread()
                        && lockMap.get(element).dec() == 0) {
                    lockMap.remove(element);
                }
            }
            free();
            return true;
        }
        return false;
    }

    private void free() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    private Thread getCurrentThread() {
        return Thread.currentThread();
    }

    record LockCounter(Thread thread, AtomicInteger counter) {
        LockCounter(Thread thread) {
            this(thread, new AtomicInteger(1));
        }

        public int inc() {
            return counter.incrementAndGet();
        }

        public int dec() {
            return counter.decrementAndGet();
        }

        public boolean isSameThread(Thread other) {
            return this.thread.equals(other);
        }

        public boolean isCurrentThread() {
            return this.thread.equals(Thread.currentThread());
        }
    }
}
