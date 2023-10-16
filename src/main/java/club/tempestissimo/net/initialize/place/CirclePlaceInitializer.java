package club.tempestissimo.net.initialize.place;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Color;
import club.tempestissimo.net.entities.attributes.Position;
import club.tempestissimo.net.initialize.AbstractInitializer;

import java.util.List;

public class CirclePlaceInitializer implements AbstractInitializer {
    public double r;
    public int centX;
    public int centY;

    /**
     * 将点位置初始化在一个圆上
     * args[0]为画图半径，int型
     * args[1]为绘图中心X轴坐标，int型
     * args[2]为绘图中心Y轴坐标，int型
     * @param net
     */
    @Override
    public void initiate(Net net) {
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            double degree = i * 2 * 3.1415926 / nodeCount;
            double posX = centX + r * Math.cos(degree);
            double posY = centY + r * Math.sin(degree);
            Position randomPosition = new Position(posX, posY, 0);
            net.getNodes().get(i).setPosition(randomPosition);
        }
    }

    public CirclePlaceInitializer(double r, int centX, int centY) {
        this.r = r;
        this.centX = centX;
        this.centY = centY;
    }
}
