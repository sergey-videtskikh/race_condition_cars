package com.plakhov;

public class Tunnel extends Stage {
    private final TunnelBreakPoint tunnelBreakPoint;

    public Tunnel(TunnelBreakPoint tunnelBreakPoint) {
        this.tunnelBreakPoint = tunnelBreakPoint;

        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                tunnelBreakPoint.goToTunnelIfAllowed(c);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                tunnelBreakPoint.exitTunnel(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
