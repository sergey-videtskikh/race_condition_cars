package com.plakhov;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final CyclicBarrier barrier;
    private final CountDownLatch countDownLatchFinish;
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

    public Car(CyclicBarrier barrier, CountDownLatch countDownLatchFinish, AtomicReference<Car> winner, Race race, int speed) {
        this.barrier = barrier;
        this.countDownLatchFinish = countDownLatchFinish;
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
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        winner.compareAndSet(null, this);
        countDownLatchFinish.countDown();
    }
}
