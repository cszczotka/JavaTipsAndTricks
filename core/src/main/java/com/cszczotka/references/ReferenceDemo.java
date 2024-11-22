package com.cszczotka.references;


import com.cszczotka.utils.GcUtils;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * SoftReference vs WeakReference
 * Garbage collector can collect an object if only weak references are pointing towards it and they are eagerly collected,
 * on the other hand Objects with SoftReference are collected when JVM absolutely needs memory.
 *
 * SoftReference looks perfect for implementing caches, so when JVM needs memory it removes object which have only SoftReference pointing towards them.
 * SoftReferences will get cleared before OutOfMemoryError is thrown
 *
 * WeakReference is great for storing meta data e.g. storing ClassLoader reference. If no class is loaded then no point in
 * keeping reference of ClassLoader, a WeakReference makes ClassLoader eligible for Garbage collection as soon as last strong reference removed.
 *
 * Object which only has Phantom reference pointing them can be collected whenever Garbage Collector likes it.
 *
 * http://antkorwin.com/concurrency/weakreference.html
 */
public class ReferenceDemo {
    public static void main(String[] args) {
        //weakReferenceDemo();
        //weakHashMapReferenceDemo();
        softReferenceDemo();
    }

    public static void weakReferenceDemo() {
        // Arrange
        String instance = new String("123");
        WeakReference<String> reference = new WeakReference<>(instance);
        // Act
        instance = null;
        System.gc();
        if(reference.get() != null) {
            new RuntimeException("Weak reference should be null");
        }
    }

    public static void weakHashMapReferenceDemo() {
        // Arrange
        WeakHashMap<String, Boolean> map = new WeakHashMap<>();
        String instance = new String("123");
        map.put(instance, true);

        // Act
        instance = null;
        GcUtils.fullFinalization();

        // Asserts
        if(!map.isEmpty()) {
            new RuntimeException("WeakHashMap should be empty");
        }
    }

    public static void softReferenceDemo() {
        // Arrange
        String instance = new String("123323");
        SoftReference<String> softReference = new SoftReference<>(instance);
        instance = null;
        if(softReference == null || softReference.get() == null) {
            new RuntimeException("SoftReference should not be null");
        }
        // Act
        GcUtils.tryToAllocateAllAvailableMemory();
        // Asserts
        if(softReference.get() != null) {
            new RuntimeException("SoftReference should be null");
        }
    }
}
