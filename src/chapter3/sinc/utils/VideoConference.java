package chapter3.sinc.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by krp on 07.07.16.
 */
public class VideoConference implements Runnable {

    private final CountDownLatch controller;

    public VideoConference(int number) {
        controller = new CountDownLatch(number);
    }
    public void arrive(String name) {
        System.out.printf("%s has arrived.\n", name);
        controller.countDown();
        System.out.printf("Video conference: waiting for %d participants.\n", controller.getCount());
    }

    @Override
    public void run() {
        System.out.printf("Video conference: Initialization: %d participants.\n", controller.getCount());
        try {
            controller.await();
            System.out.printf("Video conference: All the participants have come\n");
            System.out.printf("Video conference: Lets start...\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Participant implements Runnable {

    private String name;
    private VideoConference conference;

    public Participant(String name, VideoConference conference) {
        this.name = name;
        this.conference = conference;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
            conference.arrive(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class VideoConferenceMain {
    public static void main(String[] args) {
        VideoConference conference = new VideoConference(10);
        Thread conferenceThread = new Thread(conference);
        conferenceThread.start();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Participant("Participant_" + i, conference));
            t.start();
        }
    }
}
