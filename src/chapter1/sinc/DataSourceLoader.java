package chapter1.sinc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 05.06.16.
 */
public class DataSourceLoader implements Runnable {
    @Override
    public void run() {
        System.out.printf("Beginning data source loading: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Data source lading has finished: %s\n", new Date());
    }
}

class NetworkConnectionsLoader implements Runnable {

    @Override
    public void run() {
        System.out.printf("Beginning network source loading: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Network source lading has finished: %s\n", new Date());
    }
}