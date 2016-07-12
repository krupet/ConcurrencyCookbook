package chapter1.sinc;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 05.06.16.
 */
public class Main2 {
//    public static void main(String[] args) {
//        Thread task = new chapter1.sinc.PrimeGenerator();
//        task.start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        task.interrupt();
//    }

//    public static void main(String[] args) {
//        chapter1.sinc.FileSearch fileSearch = new chapter1.sinc.FileSearch("/home/krp", "not_sicp.pdf");
//        Thread thread = new Thread(fileSearch);
//        thread.start();
//
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        thread.interrupt();
//    }

//    public static void main(String[] args) {
//        chapter1.sinc.FileClock clock = new chapter1.sinc.FileClock();
//        chapter1.sinc.FileClock clock = new chapter1.sinc.FileClock();
//        Thread thread = new Thread(clock);
//        thread.start();
//
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread.interrupt();
//    }

//    public static void main(String[] args) {
//        Thread thread1 = new Thread(new chapter1.sinc.DataSourceLoader(), "DataSourceThread");
//        Thread thread2 = new Thread(new chapter1.sinc.NetworkConnectionsLoader(), "NetworkConnectionThread");
//
//        thread1.start();
//        thread2.start();
//
//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("chapter1.sinc.Main: Configuration has been loaded: %s\n", new Date());
//    }
//    public static void main(String[] args) {
//        Deque<Event> deque = new ArrayDeque<>();
//        chapter1.sinc.WriterTask writerTask = new chapter1.sinc.WriterTask(deque);
//        for (int i = 0; i < 3; i++) {
//            Thread thread = new Thread(writerTask);
//            thread.start();
//        }
//        CleanerTask cleanerTask = new CleanerTask(deque);
//        cleanerTask.start();
//    }


}
