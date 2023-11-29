package club.tempestissimo.examples.homework1.finalAnalyse;

import club.tempestissimo.net.analyse.AbstractAnalyser;
import club.tempestissimo.net.entities.Net;

import java.util.HashMap;
import java.util.List;

public class FinalAnalyser implements AbstractAnalyser {
    private HashMap<String, List<Double>> data;
    @Override
    public HashMap<String, List<Double>> getData() {
        return this.data;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void analyse(Net net) {

    }
}
