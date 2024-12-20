package com.cszczotka.utils;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class GcUtils {

    public static int fullFinalization() {

        final CountDownLatch finalizerLatch = new CountDownLatch(1);

        ReferenceQueue<? super Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> ref =
                new PhantomReference<>(
                        new Object() {
                            @Override
                            protected void finalize() {
                                finalizerLatch.countDown();
                            }
                        },
                        queue);

        int gcIterationCnt = awaitForLatchAndReference(finalizerLatch, ref);

        await().atMost(1, SECONDS)
                .pollInterval(10, NANOSECONDS)
                .until(() -> queue.poll() != null);

        return gcIterationCnt;
    }


    private static int awaitForLatchAndReference(CountDownLatch latch, Reference<?> reference) {

        final long deadline = System.nanoTime() + SECONDS.toNanos(1000L);
        boolean finalizationCalled = false;
        int gcIteration = 0;

        if (latch.getCount() == 0) {
            finalizationCalled = true;
        }

        while (System.nanoTime() - deadline < 0) {

            System.runFinalization();
            System.gc();
            gcIteration++;

            if (!finalizationCalled) {
                try {
                    finalizationCalled = latch.await(1L, SECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (finalizationCalled && reference.isEnqueued()) {
                return gcIteration;
            }
        }

        throw new RuntimeException("Latch failed to count down by timeout");
    }


    /**
     * This method tries to allocate maximum available memory in runtime,
     * and is catching an OutOfMemoryError.
     */
    public static void tryToAllocateAllAvailableMemory() {
        final List<Object[]> allocations = new ArrayList<>();
        try {

            int size;
            while ((size = (int) Runtime.getRuntime().freeMemory()) > 0) {
                Object[] part = new Object[Math.min(size, Integer.MAX_VALUE)];
                allocations.add(part);
            }
        } catch (OutOfMemoryError e) {
            System.out.println("catch expected exception: " + e.getMessage());
        }
    }
}
