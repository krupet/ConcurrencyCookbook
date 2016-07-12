package chapter1.sinc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 06.06.16.
 */
public class UnsafeTask implements Runnable {

    private Date startDate;

    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("starting thread: %s %s\n", Thread.currentThread().getId(), startDate);
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random()*10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread finished: %s %s\n", Thread.currentThread().getId(), startDate);
    }
}

class Core {
    public static void main(String[] args) {
//        chapter1.sinc.UnsafeTask task = new chapter1.sinc.UnsafeTask();
        SafeTask task = new SafeTask();
        for (int i = 0; i <3; i++) {
            Thread thread = new Thread(task);
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SafeTask implements Runnable {

    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
        System.out.printf("starting thread: %s %s\n", Thread.currentThread().getId(), startDate.get());
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random()*10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread finished: %s %s\n", Thread.currentThread().getId(), startDate.get());
    }
}