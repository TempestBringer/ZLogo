package club.tempestissimo.net.analyse;

import club.tempestissimo.awt.DataGraphWindow;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DegreeDistributionAnalyser implements AbstractAnalyser{
    private List<DataGraphWindow> windows = new ArrayList<>();
    private HashMap<String, List<Double>> data;

    private String graph1 = "Degree Distribution";

    private CanvasAttributes canvasAttributes = new CanvasAttributes(640, 360, 10);

    @Override
    public HashMap<String, List<Double>> getData() {
        return this.data;
    }

    @Override
    public void analyse(Net net) {
        //分析
        double[][] connectionMatrix = net.getConnectionMatrix();

        double[] perRowSum = new double[net.getNodeCount()];
        for (int i=0; i< net.getNodeCount();i++){
            perRowSum[i] = 0;
            for (int j=0; j< net.getNodeCount();j++) {
                perRowSum[i]+=connectionMatrix[i][j];
            }
        }
        List<Double> distribution = new ArrayList<>();
        for (int i=0; i< net.getNodeCount();i++){
            distribution.add(0.0);
        }
        for (int i=0; i< net.getNodeCount();i++){
//            System.out.println((int) perRowSum[i]);
            distribution.set((int) perRowSum[i],distribution.get((int) perRowSum[i])+1);
        }

        this.data.put(graph1, distribution);
//        System.out.println("data to draw: "+this.data.get(graph1).toString());
//        System.out.println("length to draw: "+this.data.get(graph1).size());
        //绘图
        this.windows.get(0).setDrawData(this.data.get(graph1));
        this.windows.get(0).update();
    }

    public DegreeDistributionAnalyser() {
        this.data = new HashMap<>();
        DataGraphWindow window = new DataGraphWindow(graph1, canvasAttributes);
        this.windows.add(window);
    }
}
