package chapter3.sinc.utils;

import com.sun.xml.internal.fastinfoset.stax.factory.StAXOutputFactory;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class MyPhaser extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
//        return super.onAdvance(phase, registeredParties);
        switch (phase) {
            case 0: return studentsArrived();
            case 1: return finishFirstExercise();
            case 2: return finishSecondExercise();
            case 3: return finishExam();
            default: return true;
        }
    }

    private boolean finishExam() {
        System.out.printf("Phaser: All students have finished the exam.\n");
        System.out.printf("Phaser: Thank you for your time.\n");
        return false;
    }

    private boolean finishSecondExercise() {
        System.out.printf("Phaser: All students have finished the second exercise.\n");
        System.out.printf("Phaser: It's time for the third one.\n");
        return false;
    }

    private boolean finishFirstExercise() {
        System.out.printf("Phaser: All students have finished the first exercise.\n");
        System.out.printf("Phaser: It's time for the second one.\n");
        return false;
    }

    private boolean studentsArrived() {
        System.out.printf("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: we have %d students.\n", getRegisteredParties());
        return false;
    }
}

class Student implements Runnable {

    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.printf("%s: Has arrived to do the exam. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Going to do the first exercise. %s\n", Thread.currentThread().getName(), new Date());
        doExercise1();
        System.out.printf("%s: Has done the first exercise. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Going to do the second exercise. %s\n", Thread.currentThread().getName(), new Date());
        doExercise2();
        System.out.printf("%s: Has done the second exercise. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Going to do the third exercise. %s\n", Thread.currentThread().getName(), new Date());
        doExercise3();
        System.out.printf("%s: Has finished the exam. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
    }

    private void doExercise3() {
        doExercise();
    }

    private void doExercise2() {
        doExercise();
    }

    private void doExercise1() {
        doExercise();
    }

    private void doExercise() {
        try {
            long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyPhaserMain {
    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        Student[] students = new Student[5];

//        for (Student student : students) {
//            student = new Student(phaser);
//            phaser.register();
//        }

        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(students[i], "Student_" + i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: The Phaser has finished: %s.\n", phaser.isTerminated());
    }
}
