package com.cszczotka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class App {
    public static void main2(String[] args) {
        List<String> list = Arrays.asList("Foo", "bar", "Foo", "Bar");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
      }

    public static void main(String[] args) {

    /*List<? super Integer> list = new ArrayList<Object>();
    list.add(7/2);
    list.add(new Integer(3));
    list.add(new Number(7/2));

     */
    }
}
