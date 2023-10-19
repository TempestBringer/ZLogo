package club.tempestissimo.net.analyse;

import club.tempestissimo.net.entities.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodeDegreeAnalyser implements  AbstractAnalyser{
    /**
     * Key String为计分项名称，List<Double>为随每帧生成的一个计分值，List中第index帧的计分值为List.get(index)
     */
    private HashMap<String, List<Double>> data;


    @Override
    public HashMap<String, List<Double>> getData() {
        return this.data;
    }

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
        String scoreboardNameOne = "Total Degree";
        if (!data.containsKey(scoreboardNameOne)){
            data.put(scoreboardNameOne,new ArrayList<>());
        }
        List<Double> resultsOne = data.get(scoreboardNameOne);
        resultsOne.add((double) sum);
        data.put(scoreboardNameOne, resultsOne);

        //计分值：max degree
        String scoreboardNameTwo = "Max Degree";
        if (!data.containsKey(scoreboardNameTwo)){
            data.put(scoreboardNameTwo,new ArrayList<>());
        }
        List<Double> resultsTwo = data.get(scoreboardNameTwo);
        resultsTwo.add((double) max);
        data.put(scoreboardNameTwo, resultsTwo);

    }

    public NodeDegreeAnalyser(){
        this.data = new HashMap<>();
    }

}
