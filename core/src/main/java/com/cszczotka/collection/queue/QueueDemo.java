package com.cszczotka.collection.queue;


import java.util.concurrent.*;

/**
 * http://javarevisited.blogspot.com/2014/06/synchronousqueue-example-in-java.html
 * http://tutorials.jenkov.com/java-util-concurrent/index.html
 */

public class QueueDemo {

    public static void main(String[] args) throws Exception {

        arrayBlockingQueue();
        synchronousQueueDemo();
        delayQueueDemo();
    }



    private static void synchronousQueueDemo() {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread producer = new Thread(() -> {
            {
                try {
                    String event = "FOUR";
                    queue.put(event); // thread will block here
                    System.out.printf("[%s] published event : %s %n", Thread
                            .currentThread().getName(), event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"PRODUCER");

        producer.start();

        Thread consumer = new Thread(() -> {
            try {
                String event = queue.take(); // thread will block here
                System.out.printf("[%s] consumed event : %s %n", Thread
                        .currentThread().getName(), event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"CONSUMER");


        consumer.start();
    }

    private static void arrayBlockingQueue() {
        BlockingQueue<String> arrayQueue = new ArrayBlockingQueue<>(2);
        arrayQueue.add("1");
        arrayQueue.add("2");
        try {
            arrayQueue.add("3");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(arrayQueue.poll());
        System.out.println(arrayQueue.poll());
        System.out.println(arrayQueue.poll());

    }

    private static void delayQueueDemo() throws Exception {
        DelayQueue queue = new DelayQueue();
        Delayed element1 = new Delayed() {
            long delay = 20000;
            long origin = System.currentTimeMillis();
            @Override
            public long getDelay(TimeUnit unit) {
                return unit.convert( delay - ( System.currentTimeMillis() - origin ), TimeUnit.MILLISECONDS );
            }

            @Override
            public int compareTo(Delayed o) {
                return 0;
            }

        };
        queue.put(element1);

        System.out.println("Try to take element from queue ...");
        Delayed element2 = queue.take();
        System.out.println("Element is taken from queue");
    }

    private static void priorityBlockingQueueDemo() {

    }
}

