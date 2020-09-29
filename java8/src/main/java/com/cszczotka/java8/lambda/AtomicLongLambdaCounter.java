package com.cszczotka.java8.lambda;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class AtomicLongLambdaCounter {

    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong();

        Function<Long, Long> myLambda = (input) -> {
            long noOfCalls = atomicLong.incrementAndGet();
            System.out.println("Lambda called " + noOfCalls + " times.");
            return input * 2;
        };

        System.out.println(myLambda.apply(1L));
        System.out.println(myLambda.apply(3L));
        System.out.println(myLambda.apply(5L));
    }
}
