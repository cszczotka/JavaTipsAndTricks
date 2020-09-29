package com.cszczotka.java8.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * http://www.baeldung.com/java-completablefuture
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        String result = calculateAsync().get();
        System.out.println(result);
        calculateAsync2();
    }

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    public static void calculateAsync2() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture.thenApply(s -> s + " World")
                .thenAccept(s -> System.out.println("Computation returned: " + s));
    }
}
