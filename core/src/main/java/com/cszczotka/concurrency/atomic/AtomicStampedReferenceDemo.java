package com.cszczotka.concurrency.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {

    public static void main(String ...args) {
        generalConceptDemo();
        compareAndSetDemo();
    }

    public static void generalConceptDemo() {
        Integer myInt = 101;
        AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(myInt, 1);

        Integer val = ref.getReference();
        Integer stamp = ref.getStamp();

        //get  atomically
        int [] stampHolder = new int[1];
        Integer val2 = ref.get(stampHolder);

        ref.set(2, 1);
        Integer val3 = ref.get(stampHolder);
    }

    public static void compareAndSetDemo() {
        Integer initRef = 101;
        AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(initRef, 1);

        boolean result = ref.compareAndSet(100, 102, 1, 2);

    }

    /**
     * A-B-A problem is when a reference is changed from pointing to A, then to B and then back to A
     */
    public static void A_B_A_IssueSolvedDemo() {

    }
}
