package chapter3.sinc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable {

    private List<String> buffer;
    private final Exchanger<List<String>> exchanger;

    public Producer(Exchanger<List<String>> exchanger, List<String> buffer) {
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int cycle = 1;

        for (int i = 0; i < 10; i++) {
            System.out.printf("Producer: Cycle %d\n", cycle);
            for (int j = 0; j < 10; j++) {
                String message = "Event " + ((i * 10) + j);
                System.out.printf("Producer: %s.\n", message);
                buffer.add(message);
            }

            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Producer: Buffer size - %d.\n", buffer.size());
            cycle++;
        }
    }
}

class Consumer implements Runnable {

    private List<String> buffer;
    private final Exchanger<List<String>> exchanger;

    public Consumer(Exchanger<List<String>> exchanger, List<String> buffer) {
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int cycle = 1;

        for (int i = 0; i < 10; i++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);

            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("Consumer: Buffer size - %d.\n", buffer.size());

            for (int j = 0; j < 10; j++) {
                System.out.printf("Consumer: Message is -> %s.\n", buffer.get(0));
                buffer.remove(0);
            }

            cycle++;
        }
    }
}

class ProducerConsumerMain {
    public static void main(String[] args) {
        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();

        Exchanger<List<String>> exchanger = new Exchanger<>();

        Producer producer = new Producer(exchanger, buffer1);
        Consumer consumer = new Consumer(exchanger, buffer2);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
