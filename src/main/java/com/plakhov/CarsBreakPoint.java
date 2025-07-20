package com.plakhov;

import static com.plakhov.MainClass.CARS_COUNT;

class CarsBreakPoint {
    private volatile int countCars = 0;
    private final String blockName;

    CarsBreakPoint(String blockName) {
        this.blockName = blockName;
    }

    public synchronized void waitOthers() {
        try {
            countCars += 1;
//            System.out.println(blockName + ": Увеличили счетчик. Жду команды");
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
//        System.out.println(": Выхожу из ожидания машины.");
    }

    public synchronized void releaseCarsBlock() {
//        System.out.println(": Жду готовности машин. Проверяю что все готовы.");
        while (countCars < CARS_COUNT) {
            try {
                wait(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//        System.out.println(": Все готовы. Отпускаю блокировки.");
        notifyAll();
    }
}
