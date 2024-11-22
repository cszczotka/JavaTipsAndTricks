package com.cszczotka.collection;

import java.util.NavigableMap;
import java.util.TreeMap;

public class NavigableMapDemo {

    public static void main(String[] args) {
        NavigableMap<String, String> navigableMap = new TreeMap<String, String>();

        navigableMap.put("C++", "Good programming language");
        navigableMap.put("Java", "Another good programming language");
        navigableMap.put("Scala", "Another JVM language");
        navigableMap.put("Python", "Language which Google use");
        navigableMap.put("Golang", "Modern system programing language");
        navigableMap.put("Typescript", "Web development");

        System.out.println("SorteMap : " + navigableMap);

        System.out.println("lowerKey from Java : " + navigableMap.lowerKey("Java"));
        System.out.println("floorKey from Java : " + navigableMap.floorKey("Java"));

        System.out.println("ceilingKey from Java: " + navigableMap.ceilingKey("Java 2"));
        System.out.println("higherKey from Java: " + navigableMap.higherKey("Java"));

        NavigableMap<String, String> headMap = navigableMap.headMap("Python", false);
        System.out.println("headMap created form navigableMap : " + headMap);

        NavigableMap<String, String> tailMap = navigableMap.tailMap("Scala", false);
        System.out.println("tailMap created form navigableMap : " + tailMap);

        NavigableMap<String, String> subMap = navigableMap.subMap("C++", false,"Python", false);
        System.out.println("subMap created form navigableMap : " + subMap);
    }
}
