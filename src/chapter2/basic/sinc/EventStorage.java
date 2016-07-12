package chapter2.basic.sinc;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by krp on 12.06.16.
 */
public class EventStorage {

    private int maxSize;
    private Deque<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.offer(new Date());
        System.out.printf("Set: %d\n", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Get: %d %s\n", storage.size(), storage.poll());
        notifyAll();
    }
}

class Producer implements Runnable {

    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.set();
        }
    }
}

class Consumer implements Runnable {


    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.get();
        }
    }
}

class MainEventStorage {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer, "Producer thread");

        Consumer consumer = new Consumer(storage);
        Thread consumerThread = new Thread(consumer, "Consumer thread");

        producerThread.start();
        consumerThread.start();
    }
}