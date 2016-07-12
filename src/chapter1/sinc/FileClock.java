package chapter1.sinc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 05.06.16.
 */
public class FileClock implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("The chapter1.sinc.FileClock has been interrupted");
            }
        }
    }
}
