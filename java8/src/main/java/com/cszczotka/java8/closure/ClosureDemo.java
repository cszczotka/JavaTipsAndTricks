package com.cszczotka.java8.closure;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * https://dzone.com/articles/java-8-lambas-limitations-closures
 *
 * "A closure is a special kind of object that combines two things: a function,
 * and the environment in which that function was created.
 * The environment consists of any local variables that were in-scope at the time that
 * the closure was created"
 */

public class ClosureDemo {

    public static void main(String[] args) throws Exception {
        int answer = 42;
        Thread t = new Thread(new Runnable() {
            public void run() {
                for(int i=0; i < 10; i++) {
                    System.out.println("The answer is: " + answer);
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        //answer = 43; // produces compilation error - Variable 'answer' is accessed from within inner class, needs to be final or effectively final
        t.join();
    }

    void fn() {
        int myVar = 42;
        Supplier<Integer> lambdaFun = () -> myVar; // error
        // myVar++;
        System.out.println(lambdaFun.get());
    }

    public static Map<String, Supplier> createCounter(int initValue) { // the enclosing scope
        int count = initValue;
        Map<String, Supplier> map = new HashMap<>();
        map.put("val", () -> count);
        //map.put("inc", () -> count++); // produces compilation error - Variable used in lambda expression should be final or effectively final
        return map;
    }


    private static class MyClosure {
        public int value;
        public MyClosure(int initValue) { this.value = initValue; }
    }

    public static Map<String, Supplier> createCounter2(int initValue) {
        MyClosure closure = new MyClosure(initValue);
        Map<String, Supplier> counter = new HashMap<>();
        counter.put("val", () -> closure.value);
        counter.put("inc", () -> closure.value++);
        return counter;
    }

    /**
     * javascript
     * function fn() { // the enclosing scope
     *     var myVar = 42;
     *     var lambdaFun = () => myVar;
     *     myVar++;
     *     console.log(lambdaFun()); // it prints 43
     * }
     *
     * function createCounter(initValue) { // the enclosing scope
     *     var count = initValue;
     *     var map = new Map();
     *     map.set('val', () => count);
     *     map.set('inc', () => count++);
     *     return map;
     * }
     * v = createCounter(42);
     * v.get('val')(); // returns 42
     * v.get('inc')(); // returns 42
     * v.get('val')(); // returns 43
     *
     */
}
