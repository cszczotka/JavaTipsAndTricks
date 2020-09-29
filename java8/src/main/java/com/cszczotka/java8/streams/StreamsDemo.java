package com.cszczotka.java8.streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class StreamsDemo {

    static void rangeDemo() {
        IntStream.rangeClosed(1, 10).forEach(num -> System.out.print(num));   // ->12345678910
        System.out.println();
        IntStream.range(1, 10).forEach(num -> System.out.print(num));   // ->123456789
        System.out.println();
    }

    static void IOStreamDemo() {
        //Creating BufferedReader for a file
        try ( BufferedReader reader = Files.newBufferedReader(Paths.get("Readme.md"), StandardCharsets.UTF_8) ) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        rangeDemo();
        IOStreamDemo();
    }
}
