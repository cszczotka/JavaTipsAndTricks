package com.cszczotka.f23;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class VThreadsDemo {

    public static void main(String[] args) throws InterruptedException {
        //demo1();
        demo2();

        Object obj = 42;
        switch (obj) {
            case Integer i -> System.out.println("Integer: " + i);
            case Long l    -> System.out.println("Long: " + l);
            default        -> System.out.println("Other: " + obj);
        }
    }


    private static void demo1() {
        Thread virtualThread = Thread.startVirtualThread(() -> {
            System.out.println("Running task in a virtual thread: "
                    + Thread.currentThread().getName());
        });

        // Waiting for virtual threads to complete
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void demo2() throws InterruptedException {
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();

        ExecutorService executor =
                Executors.newFixedThreadPool(8, virtualThreadFactory);

        for (int i = 0; i < 8; i++) {
            executor.submit(() -> {
                System.out.println("Running task in a virtual thread: "
                        + Thread.currentThread().getName());
            });
        }
        Thread.sleep(3000);
        executor.shutdown();
    }

    private static void demo3() {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> "Virtual Thread")
                .thenApplyAsync(result -> result.toUpperCase())
                .thenAcceptAsync(uppercaseResult -> {
                    System.out.println("Uppercase result: " + uppercaseResult +
                            " in thread: " + Thread.currentThread().getName());
                });

        future.join();
    }

    private static void demo4() {
        //ExecutorService executor = Executors.newVirtualThreadExecutor();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                System.out.println("Running task in a virtual thread: " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
