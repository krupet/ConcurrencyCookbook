package chapter1.sinc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 08.06.16.
 */
public class MyThreadFactory implements ThreadFactory {

    private int count;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        count = 0;
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + count);
        count++;
        stats.add(String.format("Create Thread %d with name %s on %s\n", t.getId(), t.getName(), new Date()));
        return t;
    }

    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            buffer.append(it.next()).append("\n");
        }
        return buffer.toString();
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyMain {
    public static void main(String[] args) {
        MyThreadFactory factory = new MyThreadFactory("chapter1.sinc.MyThreadFactory");
        Task task = new Task();
        Thread thread = null;
        System.out.println("Starting threads");
        for (int i = 0; i < 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }

        System.out.println("Thread factory stats");
        System.out.printf("%s\n", factory.getStats());
    }
}