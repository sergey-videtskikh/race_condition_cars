package com.plakhov;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class MainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(CARS_COUNT);
        CountDownLatch finishSignal = new CountDownLatch(CARS_COUNT);
        Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT / 2);
        AtomicReference<Car> winner = new AtomicReference<>();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(tunnelSemaphore), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(startSignal, finishSignal, winner, race, 20 + (int) (Math.random() * 10));
        }

        for (Car car : cars) {
            new Thread(car).start();
        }
        startSignal.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        finishSignal.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        System.out.println("Побдитель: " + winner.get().getName());
    }

    //Все участники должны стартовать одновременно, несмотря на разное время  подготовки.
    // В тоннель не может одновременно заехать больше половины участников (условность).
    //Попробуйте все это синхронизировать.
    //Первый участник, пересекший финишную черту, объявляется победителем (в момент пересечения этой самой черты).
    // Победитель должен быть только один (ситуация с 0 или 2+ победителями недопустима).
    //Когда все завершат гонку, нужно выдать объявление об окончании.
    //Можно корректировать классы (в том числе конструктор машин) и добавлять объекты классов из пакета java.util.concurrent.
}