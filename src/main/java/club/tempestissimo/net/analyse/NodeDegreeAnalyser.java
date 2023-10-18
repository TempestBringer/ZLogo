package club.tempestissimo.net.analyse;

import club.tempestissimo.net.entities.Net;

import java.util.HashMap;
import java.util.List;

public class NodeDegreeAnalyser implements  AbstractAnalyser{
    private HashMap<String, List<Double>> data;

    @Override
    public HashMap<String, List<Double>> getData() {
        return null;
    }

    @Override
    public void analyse(Net net) {

    }

    public NodeDegreeAnalyser(){
        this.data = new HashMap<>();
    }

}
