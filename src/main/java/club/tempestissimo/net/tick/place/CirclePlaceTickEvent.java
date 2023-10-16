package club.tempestissimo.net.tick.place;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Position;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.List;

public class CirclePlaceTickEvent implements AbstractTickEvent {
    public double r;
    public int centX;
    public int centY;

    @Override
    public void tick(Net net) {
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            double degree = i * 2 * 3.1415926 / nodeCount;
            double posX = centX + r * Math.cos(degree);
            double posY = centY + r * Math.sin(degree);
            Position randomPosition = new Position(posX, posY, 0);
            net.getNodes().get(i).setPosition(randomPosition);
        }
    }

    public CirclePlaceTickEvent(double r, int centX, int centY) {
        this.r = r;
        this.centX = centX;
        this.centY = centY;
    }
}
