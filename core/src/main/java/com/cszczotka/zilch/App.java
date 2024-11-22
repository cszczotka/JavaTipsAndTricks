package com.cszczotka.zilch;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class App {

    public static void main2(String[] args) {
        LocalDate localDate = LocalDate.of(2015,4,4);
        System.out.println(localDate.format(DateTimeFormatter.ofPattern(("MMM dd, yyyy"))));
        System.out.println(localDate.format(DateTimeFormatter.ofPattern(("E, MMM dd, yyyy"))));
        System.out.println(localDate.format(DateTimeFormatter.ofPattern(("MM/dd/yy"))));
    }

    public static void main3(String[] args) {
        SortedSet<Element> s = new TreeSet<Element>();
        s.add(new Element(15));
        s.add(new Element(10));
        s.add(new Element(25));
        s.add(new Element(10));
        System.out.println((s.first() + " " + s.size()));
    }
    public static void main4(String[] args) throws Exception {
     byte c1[] = {10,20,30,40,50};
     byte c2[] = {60,70,80,90};
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        ByteArrayOutputStream b2 = new ByteArrayOutputStream(10);
        b2.write(100);
        System.out.println("Out 1 : " + b2.size());
        b2.write(c1, 0, c2.length);
        System.out.println("Out 2 : " + b2.size());
        byte b[] = b2.toByteArray();
        System.out.println("Out 3 : " + b.length);
        b2.flush();
        System.out.println("Out 4 : " + b2.size());
        b2.reset();
        System.out.println("Out 5 : " + b2.size());
        b1.writeTo(b2);
        System.out.println("Out 6 : " + b1.size());
    }

    public static void main5(String[] args) {
        Supplier<String> i = () -> "Car";
        Consumer<String> c = x -> System.out.println(x.toLowerCase());
        Consumer<String> d = x -> System.out.println(x.toUpperCase());
        c.andThen(d).accept(i.get());
        System.out.println();

    }

    public static void main(String[] args) throws Exception {
    int x = 5;
    //x = x >> 1;
    //x.value();
    //x = !x;
       // x += 3;
        //x = ~x;
       // System.out.println(x);
        /*List<String> arr = new ArrayList<>();
        arr.forEach(System.out::println);
        for(String it : arr) {

        }
        for(int i=0; i<arr.size(); i++) {

        }*/
        File file1 = new File("");
        File file2 = new File("");
        /*FileInputStream in = new FileInputStream(file1);
        FileOutputStream out = new FileOutputStream(file2);
        in.copyTo()*/
        FileChannel in = new FileInputStream(file1).getChannel();
        FileChannel out = new FileOutputStream(file2).getChannel();
        out.transferFrom(in, 0, in.size());

        Files.copy(file1.toPath(), file2.toPath());

        PrintWriter w = new PrintWriter(file2);
        Files.lines(file1.toPath()).forEach(w::println);
        w.close();
    }
}
