package com.plakhov;

import static com.plakhov.MainClass.CARS_COUNT;

class TunnelBreakPoint {
    private volatile int countCarsInTunnel = 0;
    private final int allowedCarsInTunnel;

    TunnelBreakPoint() {
        allowedCarsInTunnel = CARS_COUNT / 2;
    }

    public synchronized void goToTunnelIfAllowed(Car car) {
        if (countCarsInTunnel < allowedCarsInTunnel) {
            countCarsInTunnel++;
//            System.out.println("Машине разрешено зайти в тоннель:" + car.getName());
        } else {
            while (countCarsInTunnel >= allowedCarsInTunnel) {
                try {
                    wait(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void exitTunnel(Car car) {
//        System.out.println("Машина выходя из тонеля уменьшает счетчик машин в тонеле: " + car.getName());
        countCarsInTunnel--;
        notify();
    }
}
