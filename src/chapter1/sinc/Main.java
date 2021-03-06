package chapter1.sinc;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello World!");

//        System.out.println(.1F + .2F);

        Thread threads [] = new Thread[10];
        Thread.State states [] = new Thread.State[10];

        for (int i = 0; i< 10; i++) {
            threads[i] = new Thread(new Calculator(i));
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread_" + i);
        }
        try (FileWriter file = new FileWriter("./log.txt"); PrintWriter pw = new PrintWriter(file);) {
            for (int i =0; i < 10; i++) {
                pw.println("chapter1.sinc.Main: status of thread " + i + ": is " + threads[i].getState());
                states[i] = threads[i].getState();
            }
            for (int i = 0; i < 10; i++) {
                threads[i].start();
            }

            boolean finish = false;
            while(!finish) {
                for (int i = 0; i < 10; i++) {
                    if (threads[i].getState() != states[i]) {
                        writeThreadInfo(pw, threads[i], states[i]);
                        states[i] = threads[i].getState();
                    }
                }
                finish = true;
                for (int i = 0; i < 10; i++) {
                    finish = finish && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
        pw.printf("chapter1.sinc.Main : Id %d - %s\n", thread.getId(), thread.getName());
        pw.printf("chapter1.sinc.Main : Priority: %d\n", thread.getPriority());
        pw.printf("chapter1.sinc.Main : Old state: %s\n", state);
        pw.printf("chapter1.sinc.Main : New state: %s\n", thread.getState());
        pw.printf("chapter1.sinc.Main : **********************************\n");
    }
}

class Calculator implements Runnable {

    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i*number);
        }
    }
}
