package com.plakhov;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static volatile Car winner = null;
    public static void main(String[] args) {
        CarsBreakPoint startBreakPoint = new CarsBreakPoint("startBreakPoint");
        CarsBreakPoint finishBreakPoint = new CarsBreakPoint("finishBreakPoint");
        TunnelBreakPoint tunnelBreakPoint = new TunnelBreakPoint();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(tunnelBreakPoint), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(startBreakPoint, finishBreakPoint, race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        startBreakPoint.releaseCarsBlock();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        finishBreakPoint.releaseCarsBlock();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!! winner = " + winner.getName());

    }

    public static synchronized void setWinner(Car car) {
        if (winner == null) {
            MainClass.winner = car;
        }
    }
}