package club.tempestissimo.net.initialize.connection;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.initialize.AbstractInitializer;

import java.util.List;

public class RandomLinkInitializer implements AbstractInitializer {

    public double possibility;
    /**
     * 以一定概率初始化无相连接图，以0和1标识连接与否
     * args[0]为概率，double型
     * @param net
     */
    @Override
    public void initiate(Net net) {
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (i<j){
                    if (Math.random()<possibility){
                        //定义一条连接
                        net.getConnectionMatrix()[i][j] = 1;
                        net.getConnectionMatrix()[j][i] = 1;
                    }else{
                        net.getConnectionMatrix()[i][j] = 0;
                        net.getConnectionMatrix()[j][i] = 0;
                    }
                }
            }

        }
    }

    public RandomLinkInitializer(double possibility) {
        this.possibility = possibility;
    }
}
