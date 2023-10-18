package club.tempestissimo.examples.homework1.tick;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;

public class SAOConnectionTickEvent implements AbstractTickEvent {
    @Override
    public void tick(Net net) {
        //每次随机选择一个节点i，改变i的出度
        //在i的所有出度中，计算：
        //  对所有其他节点，计算建立/摧毁连接后的满意度
        int nodeCount = net.getNodeCount();
//        double[][] newConnectionMatrix = new double[nodeCount][nodeCount];
        int startAgentIndex = (int)(Math.random()*nodeCount);
//        System.out.println("Selected Node ".concat(String.valueOf(startAgentIndex)).concat(" as Starter"));
        //ArrayList<Double> perAgentConnectionChangePossibility = new ArrayList<>();
        ArrayList<Double> perTargetNextSatisfaction = new ArrayList<>();
        //计算对每个目标更改连接的满意度
        for (int i = 0; i < nodeCount; i++) {
            if (i!=startAgentIndex){
                perTargetNextSatisfaction.add(evaluateConnectionQuality(net,startAgentIndex, i));
            }else{
                perTargetNextSatisfaction.add(-1E10);
            }
        }
        //选择的目标节点的index
        int targetAgentIndex = 0;
        for (int i = 0; i < nodeCount; i++) {
//            System.out.println("Satisfaction to change connection with Node ".concat(String.valueOf(i)).concat(" is ").concat(String.valueOf(perTargetNextSatisfaction.get(i))));
            if (perTargetNextSatisfaction.get(i)>perTargetNextSatisfaction.get(targetAgentIndex)){
                targetAgentIndex = i;
            }
        }
//        System.out.println("Selected Node ".concat(String.valueOf(targetAgentIndex)).concat(" as target node"));
        // 计算更改的概率
        double fenZi = Math.exp(evaluateConnectionQuality(net, startAgentIndex, targetAgentIndex));
        double fenMu = 0.0;
        for (int i=0;i<nodeCount;i++){
            if (i!=startAgentIndex){
                fenMu += Math.exp(evaluateConnectionQuality(net, startAgentIndex,i));
            }else{
                fenMu += Math.exp(0);
            }
        }
        double possibility = fenZi/fenMu;
//        System.out.println("fenZi : ".concat(String.valueOf(fenZi)));
//        System.out.println("fenMu : ".concat(String.valueOf(fenMu)));
//        System.out.println("Possibility : ".concat(String.valueOf(possibility)));
        if (Math.random()<possibility){
            //应当执行边切换
            if (Math.abs(net.getConnectionMatrix()[startAgentIndex][targetAgentIndex])<1E-5){
                net.getConnectionMatrix()[startAgentIndex][targetAgentIndex] = 1.0;
                net.getConnectionMatrix()[targetAgentIndex][startAgentIndex] = 1.0;
            }else if (Math.abs(net.getConnectionMatrix()[startAgentIndex][targetAgentIndex]-1.0)<1E-5){
                net.getConnectionMatrix()[startAgentIndex][targetAgentIndex] = 0.0;
                net.getConnectionMatrix()[targetAgentIndex][startAgentIndex] = 0.0;
            }
        }
//        if (net.getTickFrame()%10==0){
//            net.setDoGraphicsUpdate(true);
//        }
//        if (net.getTickFrame()%10==1){
//            net.setDoGraphicsUpdate(false);
//        }
    }

    /**
     * 获取连接或断开一个连接后对节点满意度的影响，即Fi(β,x(i->j))
     * @param net
     * @param sourceIndex
     * @param targetIndex
     * @return
     */
    public double evaluateConnectionQuality(Net net, int sourceIndex, int targetIndex){
        Node sourceNode = net.getNodes().get(sourceIndex);
        Node targetNode = net.getNodes().get(targetIndex);
        double sourceSatisfaction = sourceNode.calculateSatisfaction();
        if (Math.abs(net.getConnectionMatrix()[sourceIndex][targetIndex])<1E-5){
            //假设存在此链接
            net.getConnectionMatrix()[sourceIndex][targetIndex]=1.0;
            net.getConnectionMatrix()[targetIndex][sourceIndex]=1.0;
            double targetSatisfaction = sourceNode.calculateSatisfaction();
            //销毁此连接
            net.getConnectionMatrix()[sourceIndex][targetIndex]=0.0;
            net.getConnectionMatrix()[targetIndex][sourceIndex]=0.0;
//            System.out.println("Source Satisfaction : ".concat(String.valueOf(sourceSatisfaction)));
//            System.out.println("Target Satisfaction : ".concat(String.valueOf(targetSatisfaction)));
            return targetSatisfaction-sourceSatisfaction;
        } else if (Math.abs(net.getConnectionMatrix()[sourceIndex][targetIndex]-1)<1E-5) {
            //假设没有此链接
            net.getConnectionMatrix()[sourceIndex][targetIndex]=0.0;
            net.getConnectionMatrix()[targetIndex][sourceIndex]=0.0;
            double targetSatisfaction = sourceNode.calculateSatisfaction();
            //还原此连接
            net.getConnectionMatrix()[sourceIndex][targetIndex]=1.0;
            net.getConnectionMatrix()[targetIndex][sourceIndex]=1.0;
//            System.out.println("Source Satisfaction : ".concat(String.valueOf(sourceSatisfaction)));
//            System.out.println("Target Satisfaction : ".concat(String.valueOf(targetSatisfaction)));
            return targetSatisfaction-sourceSatisfaction;
        }else{
            return 0.0;
        }

    }

    /**
     * 深拷贝一个连接矩阵
     * @param nodeCount
     * @param sourceMatrix
     * @return
     */
    public double[][] copyConnectionMatrix(int nodeCount, double[][] sourceMatrix){
        double[][] newConnectionMatrix = new double[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                newConnectionMatrix[i][j] = sourceMatrix[i][j];
            }
        }
        return newConnectionMatrix;
    }
}
