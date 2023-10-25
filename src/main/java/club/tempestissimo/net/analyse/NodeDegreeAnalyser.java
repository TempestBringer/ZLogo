package club.tempestissimo.net.analyse;

import club.tempestissimo.awt.DataGraphWindow;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 全节点出度和与最大出度节点的分析器，适用于有向与无向图
 */

public class NodeDegreeAnalyser implements  AbstractAnalyser{
    /**
     * Key String为计分项名称，List<Double>为随每帧生成的一个计分值，List中第index帧的计分值为List.get(index)
     */
    private HashMap<String, List<Double>> data;
    private List<DataGraphWindow> windows = new ArrayList<>();

    private String graph1 = "Total Degree";
    private String graph2 = "Max Degree";

    private CanvasAttributes canvasAttributes = new CanvasAttributes(640, 360, 10);


    @Override
    public HashMap<String, List<Double>> getData() {
        return this.data;
    }

    /**
     * 分析器被调用的函数
     * @param net
     */
    @Override
    public void analyse(Net net) {
        HashMap<Integer,Double> rawResult = new HashMap<>();
        double[][] connectionMatrix = net.getConnectionMatrix();
        int sum=0; double max=0;
        int nodeCount = net.getNodeCount();
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (Math.abs(connectionMatrix[i][j]-1.0)<1E-5){
                    sum+=1;
                    if (rawResult.containsKey(i)){
                        rawResult.put(i,rawResult.get(i)+1.0);
                    }else{
                        rawResult.put(i,1.0);
                    }
                }
            }
        }
        for(Integer i: rawResult.keySet()){
//            System.out.println(String.valueOf(i).concat(", ").concat(String.valueOf(rawResult.get(i))));
            if (rawResult.get(i)>max){
                max = rawResult.get(i);
            }
        }
        //计分值：total degree
        String scoreboardNameOne = graph1;
        if (!data.containsKey(scoreboardNameOne)){
            data.put(scoreboardNameOne,new ArrayList<>());
        }
        List<Double> resultsOne = data.get(scoreboardNameOne);
        resultsOne.add((double) sum);
        data.put(scoreboardNameOne, resultsOne);

        //计分值：max degree
        String scoreboardNameTwo = graph2;
        if (!data.containsKey(scoreboardNameTwo)){
            data.put(scoreboardNameTwo,new ArrayList<>());
        }
        List<Double> resultsTwo = data.get(scoreboardNameTwo);
        resultsTwo.add((double) max);
        data.put(scoreboardNameTwo, resultsTwo);

        //更新图表
        this.windows.get(0).setDrawData(this.data.get(graph1));
        this.windows.get(1).setDrawData(this.data.get(graph2));
        for (DataGraphWindow window: windows){
            window.update();
        }
    }

    public NodeDegreeAnalyser(){
        this.data = new HashMap<>();
        DataGraphWindow window1 = new DataGraphWindow(graph1, canvasAttributes);
        DataGraphWindow window2 = new DataGraphWindow(graph2, canvasAttributes);

        this.windows.add(window1);
        this.windows.add(window2);

    }

}
