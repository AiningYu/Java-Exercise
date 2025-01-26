package com.example.demo.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private static int count = 0;
    private final int MAX_CAPACITY = 10;

    public void produce() throws InterruptedException {
        lock.lock();
        try {
            while (count == MAX_CAPACITY) {
                condition.await();
            }
            count++;
            System.out.println(Thread.currentThread().getName() + " Produced, counter " + count);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void process() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                condition.await();
            }
            count--;
            System.out.println(Thread.currentThread().getName() + " Consumed, count: " + count);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();

        Thread producer = new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    test.produce();
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }, "Producer");

        Thread consumer = new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {
                    test.process();
                    Thread.sleep(150);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed. Main thread exiting.");

    }
}
