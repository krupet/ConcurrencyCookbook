package chapter1.sinc;

import java.io.File;

/**
 * Created by krp on 05.06.16.
 */
public class FileSearch implements Runnable {

    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.printf("Starting: %s %s\n", initPath, fileName);
        File file = new File(initPath);
        System.out.printf("FileName: %s %b\n", file.getAbsolutePath(), file.isDirectory());
        if (file.isDirectory()) {
            try {
                System.out.println("Starting search");
                directoryProcess(file);
            } catch (InterruptedException ex) {
                System.out.printf("%s: the search has been interrupted", Thread.currentThread().getName());
            }
        }
    }

    private void directoryProcess(File file) throws InterruptedException {
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++){
                if (files[i].isDirectory()) {
                    directoryProcess(files[i]);
                } else {
                    fileProcess(files[i]);
                }
            }
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void fileProcess(File file) throws InterruptedException {
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        if (Thread.interrupted()) {
            System.out.println("before throwing");
            throw new InterruptedException();
        }
    }
}
