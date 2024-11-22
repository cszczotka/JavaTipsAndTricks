package com.cszczotka.f23;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class StructuredConcurrencyDemo {

    public StructuredConcurrencyDemo(String s, Integer integer) {

    }

    public static void main(String[] args) {

    }

    StructuredConcurrencyDemo handle() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String> user = scope.fork(() -> findUser());
            Supplier<Integer> order = scope.fork(() -> fetchOrder());

            scope.join()            // Join both subtasks
                    .throwIfFailed();  // Propagate errors if any

            // Both subtasks have succeeded, compose their results
            return new StructuredConcurrencyDemo(user.get(), order.get());
        }
    }

    private String findUser() {
        return "";
    }

    private Integer fetchOrder() {
        return 0;
    }
}
