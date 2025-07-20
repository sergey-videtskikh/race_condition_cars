package com.plakhov;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private final CarsBreakPoint startBreakPoint;
    private final CarsBreakPoint finishBreakPoint;
    private Race race;
    private int speed;
    private String name;



    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(CarsBreakPoint startBreakPoint, CarsBreakPoint finishBreakPoint, Race race, int speed) {
        this.startBreakPoint = startBreakPoint;
        this.finishBreakPoint = finishBreakPoint;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            startBreakPoint.waitOthers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        MainClass.setWinner(this);
        finishBreakPoint.waitOthers();

    }
}
