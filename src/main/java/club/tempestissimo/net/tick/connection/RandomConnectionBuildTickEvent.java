package club.tempestissimo.net.tick.connection;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.List;

public class RandomConnectionBuildTickEvent implements AbstractTickEvent {

    public double possibility;

    /**
     * 选取无向网络中的边并随机建立和销毁该连边，该连边的权重由0与1表示
     * args[0]代表选择的概率，double型
     * @param net
     */
    @Override
    public void tick(Net net) {
        for (int i = 0; i < net.getNodeCount();i++){
            for (int j = 0; j < net.getNodeCount();j++){
                if (i<j){
                    if (Math.random()<possibility){
                        if (Math.abs(net.getConnectionMatrix()[i][j]-1)<1E-5){
                            net.getConnectionMatrix()[i][j] = 0;
                            net.getConnectionMatrix()[j][i] = 0;
                        }else if(Math.abs(net.getConnectionMatrix()[i][j])<1E-5){
                            net.getConnectionMatrix()[i][j] = 1;
                            net.getConnectionMatrix()[j][i] = 1;
                        }

                    }
                }
            }
        }
    }

    public RandomConnectionBuildTickEvent(double possibility) {
        this.possibility = possibility;
    }
}
