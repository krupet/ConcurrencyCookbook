package chapter3.sinc.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileSearch implements Runnable {

    private String initPath;
    private String fileExtension;
    private List<String> results = new ArrayList<>();
    private Phaser phaser;

    public FileSearch(String initPath, String fileExtension, Phaser phaser) {
        this.initPath = initPath;
        this.fileExtension = fileExtension;
        this.phaser = phaser;
    }

    private void directoryProcess(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) directoryProcess(files[i]);
                else fileProcess(files[i]);
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(fileExtension)) {
            results.add(file.getAbsolutePath());
        }
    }

    private void filterResults() {
        final long currentTime = System.currentTimeMillis();
        results = results.stream()
                .filter((e) -> new File(e).lastModified() - currentTime < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))
                .collect(Collectors.toList());
    }

    private boolean checkResults() {
        if (results.isEmpty()) {
            System.out.printf("%s: phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
            System.out.printf("%s: phase %d: END.\n", Thread.currentThread().getName(), phaser.getPhase());
            phaser.arriveAndDeregister();
            return false;
        } else {
            System.out.printf("%s: phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(), results.size());
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    private void showInfo() {
        results.forEach(s -> System.out.printf("%s: %s\n", Thread.currentThread().getName(), new File(s).getAbsolutePath()));
        phaser.arriveAndAwaitAdvance();
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Starting\n", Thread.currentThread().getName());
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }

        if (!checkResults()) return;

        filterResults();

        if (!checkResults()) return;

        showInfo();

        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Task completed.\n", Thread.currentThread().getName());
    }
}

class FileSearchMain {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);
        FileSearch txt = new FileSearch("/home", "txt", phaser);
        FileSearch log = new FileSearch("/var/log", "log", phaser);
        FileSearch pdf = new FileSearch("/home", "pdf", phaser);

        Thread txtThread = new Thread(txt, "TXT");
        txtThread.start();
        Thread logThread = new Thread(log, "LOG");
        logThread.start();
        Thread pdfThread = new Thread(pdf, "PDF");
        pdfThread.start();

        try {
            txtThread.join();
            logThread.join();
            pdfThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Terminated: " + phaser.isTerminated());


    }
}
