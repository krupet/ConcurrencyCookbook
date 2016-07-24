package chapter4.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Result {

    private String name;
    private int value;

    public Result(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class Task0 implements Callable<Result> {

    private String name;

    public Task0(String name) {
        this.name = name;
    }

    @Override
    public Result call() throws Exception {
        System.out.printf("%s: Starting.\n", this.name);
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Waiting %d seconds for result.\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }
        System.out.printf("%s: Ends.\n", this.name);
        return new Result(this.name, value);
    }
}

class ResultMain {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Task0> taskList = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            Task0 task = new Task0("Task_" + i);
            taskList.add(task);
        }

        List<Future<Result>> resultList = null;
        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        System.out.printf("Main: Printing the results.\n");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Result> future = resultList.get(i);
            try {
                Result result = future.get();
                System.out.printf("%s: Value -> %d.\n", result.getName(), result.getValue());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
