package club.tempestissimo.examples.homework1.tick;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.tick.AbstractTickEvent;

public class TickStopEvent implements AbstractTickEvent {
    private int tickStop=1000;
    @Override
    public void tick(Net net) {
        if (net.getTickFrame()==tickStop){
            net.setDoTick(false);
        }
        if (net.getTickFrame()%100==0){
            System.out.println(net.getBaseName().concat(": ").concat(String.valueOf((float)net.getTickFrame()/tickStop*100)).concat("%"));
        }
    }

    public TickStopEvent(int tickStop) {
        this.tickStop = tickStop;
    }

    public int getTickStop() {
        return tickStop;
    }

    public void setTickStop(int tickStop) {
        this.tickStop = tickStop;
    }
}
