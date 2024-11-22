package com.cszczotka.concurrency.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(4);

        es.submit(() -> {
            try {
                System.out.println("Task 1 starting ");
                Thread.sleep(20000);
                System.out.println("Task 1 ended");
            } catch (Exception e) {}
         });

        es.submit(() -> {
            try {
                System.out.println("Task 2 starting ");
                Thread.sleep(3000);
                System.out.println("Task 2 ended");
            } catch (Exception e) {}
        });

        es.submit(() -> {
            try {
                System.out.println("Task 3 starting ");
                Thread.sleep(20000);
                System.out.println("Task 3 ended");
            } catch (Exception e) {}
        });

        es.submit(() -> {
            try {
                System.out.println("Task 4 starting ");
                Thread.sleep(30000);
                System.out.println("Task 4 ended");
            } catch (Exception e) {}
        });

        //es.awaitTermination(60, TimeUnit.SECONDS);
        es.shutdown();
    }
}
