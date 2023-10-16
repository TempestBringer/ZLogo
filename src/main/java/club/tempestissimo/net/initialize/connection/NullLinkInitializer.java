package club.tempestissimo.net.initialize.connection;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.initialize.AbstractInitializer;

import java.util.List;

public class NullLinkInitializer implements AbstractInitializer {
    @Override
    public void initiate(Net net) {
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (i<=j){
                    net.getConnectionMatrix()[i][j] = 0;
                    net.getConnectionMatrix()[j][i] = 0;
                }
            }

        }
    }
}
