package club.tempestissimo.net.initialize.connection;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.initialize.AbstractInitializer;

/**
 * 星型网络初始化器，初始化无向网络为星形
 */
public class StarLinkInitializer implements AbstractInitializer {
    @Override
    public void initiate(Net net) {
        int randCenter = (int) (Math.random()*net.getNodeCount());
        for (int i=0;i<net.getNodeCount();i++){
            if (randCenter!=i){
                net.getConnectionMatrix()[randCenter][i]=1.0;
                net.getConnectionMatrix()[i][randCenter]=1.0;
            }
        }
    }
}
