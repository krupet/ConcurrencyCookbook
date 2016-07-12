package chapter2.basic.sinc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by krp on 12.06.16.
 */
public class PrintQueue {

//    private final Lock dequeueLock = new ReentrantLock();
    private final Lock dequeueLock = new ReentrantLock();

//    public void printJob(Object document) {
//        dequeueLock.lock();
//        try {
//            long duration = (long) (Math.random() * 10000);
//            System.out.println(Thread.currentThread().getName() + ": Print queue: Printing a job during " +
//                    (duration/1000) + "seconds");
//            Thread.sleep(duration);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            dequeueLock.unlock();
//        }
//    }

    public void printJob(Object document) {
        dequeueLock.lock();
        try {
            long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() + ": Print queue: Printing a job block 1 during " +
                    (duration/1000) + "seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dequeueLock.unlock();
        }

        dequeueLock.lock();
        try {
            long duration = (long) (Math.random() * 10000);
            System.out.println(Thread.currentThread().getName() + ": Print queue: Printing a job block 2 during " +
                    (duration/1000) + "seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dequeueLock.unlock();
        }
    }
}

class Job implements Runnable {

    public PrintQueue queue;

    public Job(PrintQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
        queue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }
}

class MainQueue {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();

        Thread threads[] = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread_" + i);
        }

//        for (Thread thread : threads) {
//            thread.start();
//        }

        for (Thread thread : threads) {
            thread.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}