package chapter2.basic.sinc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by krp on 12.06.16.
 */
public class PriceInfo {

    private double price1;
    private double price2;

    private final ReadWriteLock lock;

    public PriceInfo() {
        price1 = 0.1;
        price2 = 0.2;

        lock = new ReentrantReadWriteLock();
    }

    public double getPrice1() {
        lock.readLock().lock();
        double value = price1;
        lock.readLock().unlock();
        return value;
    }

    public double getPrice2() {
        lock.readLock().lock();
        double value = price2;
        lock.readLock().unlock();
        return value;
    }

    public void setPrices(double price1, double price2) {
        lock.writeLock().lock();
        this.price1 = price1;
        this.price2 = price2;
        lock.writeLock().unlock();
    }
}

class Reader implements Runnable {

    private PriceInfo priceInfo;

    public Reader(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: Price 1: %f\n", Thread.currentThread().getName(), priceInfo.getPrice1());
            System.out.printf("%s: Price 2: %f\n", Thread.currentThread().getName(), priceInfo.getPrice2());
        }
    }
}

class Writer implements Runnable {

    private PriceInfo priceInfo;

    public Writer(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.printf("Writer. Attempt to modify the prices\n");
            priceInfo.setPrices(Math.random()*10, Math.random()*8);
            System.out.println("Writer. Prices has been modified");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MainPricesInfo {
    public static void main(String[] args) {
        PriceInfo priceInfo = new PriceInfo();

        Reader readers [] = new Reader[5];
        Thread [] readerThreads = new Thread[5];

        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Reader(priceInfo);
            readerThreads[i] = new Thread(readers[i]);
        }

        Writer writer = new Writer(priceInfo);
        Thread writerThread = new Thread(writer);

        for (Thread reader : readerThreads) {
            reader.start();
        }
        writerThread.start();
    }
}