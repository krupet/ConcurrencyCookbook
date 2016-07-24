package chapter4.executors;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//#1
//public class Task1 implements Callable<String> {

//#2
public class Task1 implements Runnable {

    private String name;

    public Task1(String name) {
        this.name = name;
    }

    @Override
//    #1
//    public String call() throws Exception {

//    #2
    public void run() {
        System.out.printf("%s: Starting at: %s.\n", name, new Date());
//        #1
//        return "Hello World!";
    }
}

class Task1Main {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s.\n", new Date());

//        #1
//        for (int i = 0; i < 5; i++) {
//            executor.schedule(new Task1("Task_" +i), i + 1, TimeUnit.SECONDS);
//        }

//        #2
        Task1 task = new Task1("Repeated Task");
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {
            System.out.printf("Main: Delay: %s.\n", future.getDelay(TimeUnit.SECONDS));

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

//        #1
//        try {
//            executor.awaitTermination(1, TimeUnit.DAYS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.printf("Main: Ended at: %s.\n", new Date());
    }
}
