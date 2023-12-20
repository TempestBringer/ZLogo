package club.tempestissimo.examples.homework1.tick;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.tick.AbstractTickEvent;

public class AutoClearAllConnectionEvent implements AbstractTickEvent {

    @Override
    public void tick(Net net) {
        if (net.getTickFrame() % 5000 == 0){
            for (int i = 0; i < net.getNodeCount(); i++) {
                for (int j = 0; j < net.getNodeCount(); j++) {
                    net.getConnectionMatrix()[i][j] = 0;
                }
            }
        }
    }
}
