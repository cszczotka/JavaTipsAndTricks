package com.cszczotka.java8.closure;

import java.util.function.Function;

public class AreLambdasClosures2 {

    class MyInt {
        int i = 0;
    }

    public Function<Integer, Integer> make_fun() {
        // Outside the scope of the returned function:
        final MyInt n =  new MyInt();
        return arg -> {
            System.out.print(n.i + "+" + arg + ": ");
            arg += 1;
            n.i += arg;
            return n.i;
        };
    }



    public void try_it() {
        Function<Integer, Integer>
                x = make_fun(),
                y = make_fun();
        for(int i = 0; i < 5; i++)
            System.out.println(x.apply(i));
        for(int i = 10; i < 15; i++)
            System.out.println(y.apply(i));
    }

    public static void main(String[] args) {
        new AreLambdasClosures2().try_it();
    }


}
