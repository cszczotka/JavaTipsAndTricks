package com.cszczotka.java16;

public class PatternMatchingDemo {

    static record Human (String name, int age, String profession) {}
    static record Circle() {}
    static record Shape() {}

    public static String checkObject(Object obj) {
        return switch (obj) {
            case Human h -> "Name: %s, age: %s and profession: %s".formatted(h.name(), h.age(), h.profession());
            case Circle c -> "This is a circle";
            case Shape s -> "It is just a shape";
            case null -> "It is null";
            default -> "It is an object";
        };
    }

    public static void main(String[] args) {
        var s = new Shape();
        var result = checkObject(s);
        System.out.println(result);
    }


}
