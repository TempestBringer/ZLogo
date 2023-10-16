package club.tempestissimo.net.tick.node;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.List;

public class NodeCountChangeTickEvent implements AbstractTickEvent {
    @Override
    public void tick(Net net) {
        net.changeNodeCount(net.getNodeCount()+1);
        if (net.getNodeCount()%1000==0)
            net.setDoGraphicsUpdate(true);
        if (net.getNodeCount()%1000==1)
            net.setDoGraphicsUpdate(false);

    }
}
