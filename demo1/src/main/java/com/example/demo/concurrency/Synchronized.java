package com.example.demo.concurrency;

import java.lang.*;
public class Synchronized {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        System.out.println("Counter starts with: {}");
        System.out.println(counter.getCounter());
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        counter.multiple();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        counter.divide();
                    }
                }
        );
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Counter ends with: {}");
        System.out.println(counter.getCounter());
    }
}

class Counter{
    private Double counter = 1.0;
    private final Object lock = new Object();
    public Double getCounter() {
        return counter;
    }
    public void increment() {
        counter++;
    }
    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }
    public void divide() {
        synchronized (lock) {
            counter /= 2;
        }
    }
    public void multiple() {
        synchronized (lock) {
            counter *= 2;
        }
    }
}