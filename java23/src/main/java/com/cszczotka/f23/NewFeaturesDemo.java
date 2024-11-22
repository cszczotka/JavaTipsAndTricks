package com.cszczotka.f23;

public class NewFeaturesDemo {

    public static void main(String[] args) throws InterruptedException {
        demoPrimitiveTypePatterns();
    }


    private static void demoPrimitiveTypePatterns() {
        Object obj = 42;
        switch (obj) {
            case Integer i -> System.out.println("Integer: " + i);
            case Long l    -> System.out.println("Long: " + l);
            default        -> System.out.println("Other: " + obj);
        }
    }


}
