package club.tempestissimo.net.analyse;

import club.tempestissimo.net.entities.Net;

import java.util.HashMap;
import java.util.List;

public interface AbstractAnalyser {
    public abstract HashMap<String, List<Double>> getData();
    public abstract void clearData();
    public abstract void analyse(Net net);

}
