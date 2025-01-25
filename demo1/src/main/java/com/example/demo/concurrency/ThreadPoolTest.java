package com.example.demo.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void compute(long n) {
        long result = 0;
        for (int i = 1; i <= n; i++) {
            result += i*i;
        }
    }

    public static void runThreads(int taskNumber, long n ) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(taskNumber);

        for (int i = 0; i < taskNumber; i++) {
            executor.execute(() -> compute(n));
        }

        executor.shutdown();

        while(!executor.isTerminated()){}
    }

    public static void main(String[] args) throws InterruptedException{
        int taskCount = 8;
        long n = 10000000L;

        System.out.println("Running with Threads...");
        long start = System.currentTimeMillis();
        runThreads(taskCount, n);
        long end = System.currentTimeMillis();
        System.out.println("Threads Time Taken: " + (end - start) + " ms");
    }
}
