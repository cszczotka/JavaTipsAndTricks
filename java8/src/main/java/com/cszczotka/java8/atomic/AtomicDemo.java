package com.cszczotka.java8.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class AtomicDemo {
    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    public static void main(String... args) throws Exception {
        //demoLongAdderSharedBetweenThreads();
        demoLongAccumulator();
    }

    static void demoLongAdderSharedBetweenThreads() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        LongAdder adder = new LongAdder();
        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(adder::increment));

        executor.awaitTermination(10, TimeUnit.SECONDS);
        executor.shutdown();

        System.out.println(adder.sumThenReset());   // => 1000

    }

    static void demoLongAccumulator() {
        long init = 10;
        LongAccumulator accumulator1 = new LongAccumulator((a, b) -> a+b, init);
        accumulator1.accumulate(1);
        accumulator1.accumulate(2);
        accumulator1.accumulate(3);



        assert accumulator1.get() == 16;

        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        assert  longAdder.longValue() == 1;
    }
}
