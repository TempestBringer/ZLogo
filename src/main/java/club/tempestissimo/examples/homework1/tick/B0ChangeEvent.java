package club.tempestissimo.examples.homework1.tick;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.tick.AbstractTickEvent;

public class B0ChangeEvent implements AbstractTickEvent {
    //B0参数搜索范围
    private double argStart = 0.01;
    private double argStep = 0.01;
    private double argStop = 0.45;
    private double argCurrent = argStart;
    private boolean reachedMaxArg = false;

    @Override
    public void tick(Net net) {
        if (net.getTickFrame() % 5000 == 0){
            if(!this.reachedMaxArg){
                argCurrent += argStep;
                if (argCurrent>argStop){
                    reachedMaxArg = true;
                }
            }else{
                argCurrent -= argStep;
                if (argCurrent<=0.0){
                    net.setDoTick(false);
                }
            }
            for(Node node:net.getNodes()){
                node.getPreference().setBalancePreferenceB0(argCurrent);
            }
        }
    }
}
