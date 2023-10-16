package club.tempestissimo.net.initialize.place;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.entities.attributes.Color;
import club.tempestissimo.net.entities.attributes.Position;
import club.tempestissimo.net.initialize.AbstractInitializer;

import java.util.List;

public class RandomPlaceInitializer implements AbstractInitializer {
    public int xPadding;
    public int yPadding;
    public int xLength;
    public int yLength;
    /**
     * 随机初始化所有点的位置
     * xPadding为到左右两边最低距离
     * yPadding为到左右两边最低距离
     * xLength为最大X，int型
     * yLength为最大Y，int型
     * @param net
     */
    @Override
    public void initiate(Net net) {
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            double randomX = xPadding + Math.random()*(xLength - 2 * xPadding);
            double randomY = yPadding + Math.random()*(yLength - 2 * yPadding);
            Position randomPosition = new Position(randomX,randomY,0);
            net.getNodes().get(i).setPosition(randomPosition);
        }
    }

    public RandomPlaceInitializer(int xPadding, int yPadding, int xLength, int yLength) {
        this.xPadding = xPadding;
        this.yPadding = yPadding;
        this.xLength = xLength;
        this.yLength = yLength;
    }
}
