package com.plakhov;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final CountDownLatch startSignal;
    private final CountDownLatch finishSignal;
    private final AtomicReference<Car> winner;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(CountDownLatch startSignal, CountDownLatch finishSignal, AtomicReference<Car> winner, Race race, int speed) {
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
        this.winner = winner;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            startSignal.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitStart();
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        winner.compareAndSet(null, this);
        finishSignal.countDown();
    }

    private void waitStart() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
