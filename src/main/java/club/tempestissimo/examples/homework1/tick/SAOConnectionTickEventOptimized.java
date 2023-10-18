package club.tempestissimo.examples.homework1.tick;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;

public class SAOConnectionTickEventOptimized implements AbstractTickEvent {

    public boolean doLog = false;

    public int threadNum = 6;
    /**
     * 一阶段寻找最大改善满意度的连接目标的线程池以及结果变量
     */
    ArrayList<NodeNextSatisfactionCalculateThread>  nextSatisfactionCalculateThreads= new ArrayList<>();
    ArrayList<Double> nextSatisfactionCalculateThreadResult = new ArrayList<>();

    public class NodeNextSatisfactionCalculateThread extends Thread{
        int threadId;
        boolean isDone = false;
        /**
         * 指向源网络的指针,不应该对此网络直接操作
         */
        Net net;
        int sourceIndex;
        @Override
        public void run() {
            if (doLog)
                System.out.println("Thread ".concat(String.valueOf(threadId)).concat(" Running"));
            //复制出一份网络
            Net net = this.net.copy();
//            double connectionMatrix[][] = new double[net.getNodeCount()][net.getNodeCount()];
//            for (int j = 0; j < net.getNodeCount(); j++) {
//                for (int k = 0; k < net.getNodeCount(); k++) {
//                    connectionMatrix[j][k]=net.getConnectionMatrix()[j][k];
//                }
//            }

            for (int i=threadId;i<net.getNodeCount();i+=threadNum){
                int targetIndex = i;
                if (sourceIndex==targetIndex){
                    nextSatisfactionCalculateThreadResult.set(targetIndex,-1E10);
                }
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
                    if (doLog){
                        System.out.println("Source Satisfaction : ".concat(String.valueOf(sourceSatisfaction)));
                        System.out.println("Target Satisfaction : ".concat(String.valueOf(targetSatisfaction)));

                    }
                    nextSatisfactionCalculateThreadResult.set(targetIndex,targetSatisfaction-sourceSatisfaction);
                } else if (Math.abs(net.getConnectionMatrix()[sourceIndex][targetIndex]-1)<1E-5) {
                    //假设没有此链接
                    net.getConnectionMatrix()[sourceIndex][targetIndex]=0.0;
                    net.getConnectionMatrix()[targetIndex][sourceIndex]=0.0;
                    double targetSatisfaction = sourceNode.calculateSatisfaction();
                    //还原此连接
                    net.getConnectionMatrix()[sourceIndex][targetIndex]=1.0;
                    net.getConnectionMatrix()[targetIndex][sourceIndex]=1.0;
                    if (doLog){
                        System.out.println("Source Satisfaction : ".concat(String.valueOf(sourceSatisfaction)));
                        System.out.println("Target Satisfaction : ".concat(String.valueOf(targetSatisfaction)));

                    }
                    nextSatisfactionCalculateThreadResult.set(targetIndex,targetSatisfaction-sourceSatisfaction);
                }else{
                    nextSatisfactionCalculateThreadResult.set(targetIndex,1E-10);
                }
            }
            if (doLog)
                System.out.println("Thread ".concat(String.valueOf(threadId)).concat(" Calculate Finshed"));
            isDone = true;
        }

        public NodeNextSatisfactionCalculateThread(int threadId, Net net, int sourceIndex) {
            this.threadId = threadId;
            this.net = net;
            this.sourceIndex = sourceIndex;
        }
    }
    @Override
    public void tick(Net net){
        //每次随机选择一个节点i，改变i的出度
        //在i的所有出度中，计算：
        //  对所有其他节点，计算建立/摧毁连接后的满意度
        int nodeCount = net.getNodeCount();
//        double[][] newConnectionMatrix = new double[nodeCount][nodeCount];
        int startAgentIndex = (int)(Math.random()*nodeCount);
//        System.out.println("Selected Node ".concat(String.valueOf(startAgentIndex)).concat(" as Starter"));

        //多线程计算对每个目标更改连接的满意度,首先重置变量
        nextSatisfactionCalculateThreads= new ArrayList<>();
        nextSatisfactionCalculateThreadResult = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            nextSatisfactionCalculateThreadResult.add(-1e10);
        }
        for (int i=0;i<threadNum;i++){
            nextSatisfactionCalculateThreads.add(new NodeNextSatisfactionCalculateThread(i,net,startAgentIndex));
            nextSatisfactionCalculateThreads.get(i).start();
            }
        while(true){
            boolean flag = true;
            for (int i=0;i<threadNum;i++){
                if (!nextSatisfactionCalculateThreads.get(i).isDone){
                    flag = false;
                    break;
                }
            }
            if (flag){
                break;
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        if (doLog)
            System.out.println("Phase 1 Finished");
        //选择的目标节点的index
        int targetAgentIndex = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (doLog)
                System.out.println("Satisfaction to change connection with Node ".concat(String.valueOf(i)).concat(" is ").concat(String.valueOf(nextSatisfactionCalculateThreadResult.get(i))));
            if (nextSatisfactionCalculateThreadResult.get(i)>nextSatisfactionCalculateThreadResult.get(targetAgentIndex)){
                targetAgentIndex = i;
            }
        }
        if (doLog)
            System.out.println("Selected Node ".concat(String.valueOf(targetAgentIndex)).concat(" as target node"));
        // 多线程计算更改的概率
        double fenZi = Math.exp(evaluateConnectionQuality(net, startAgentIndex, targetAgentIndex));

        nextSatisfactionCalculateThreads= new ArrayList<>();
        nextSatisfactionCalculateThreadResult = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            nextSatisfactionCalculateThreadResult.add(-1e10);
        }
        for (int i=0;i<threadNum;i++){
            nextSatisfactionCalculateThreads.add(new NodeNextSatisfactionCalculateThread(i,net,startAgentIndex));
            nextSatisfactionCalculateThreads.get(i).start();
        }
        while(true){
            boolean flag = true;
            for (int i=0;i<threadNum;i++){
                if (!nextSatisfactionCalculateThreads.get(i).isDone){
                    flag = false;
                    break;
                }
            }
            if (flag){
                break;
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        double fenMu = 0.0;
        for (int i = 0; i < nodeCount; i++) {
            fenMu+=Math.exp(nextSatisfactionCalculateThreadResult.get(i));
        }
        double possibility = fenZi/fenMu;
        if (doLog){
            System.out.println("fenZi : ".concat(String.valueOf(fenZi)));
            System.out.println("fenMu : ".concat(String.valueOf(fenMu)));
            System.out.println("Possibility : ".concat(String.valueOf(possibility)));
        }
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
