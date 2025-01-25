package com.example.demo.concurrency;

import java.util.*;

public class ThreadTest{
    public static void compute(long n) {
        long result = 0;
        for (int i = 1; i <= n; i++) {
            result += i*i;
        }
    }

    public static void runThreads(int taskNumber, long n ) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < taskNumber; i++) {
            Thread thread = new Thread(()->{compute(n);});
            threads.add(thread);
            thread.start();
        }

        for (Thread thread: threads) {
            thread.join();
        }
    }

    public static void main (String[] args) throws InterruptedException {
        int taskCount = 8;
        long n = 10000000L;

        System.out.println("Running with Threads...");
        long start = System.currentTimeMillis();
        runThreads(taskCount, n);
        long end = System.currentTimeMillis();
        System.out.println("Threads Time Taken: " + (end - start) + " ms");
    }
}