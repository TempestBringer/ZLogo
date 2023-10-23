package club.tempestissimo.net.entities;

import club.tempestissimo.awt.MainWindow;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.analyse.AbstractAnalyser;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Net implements Runnable{

    /**
     * 节点数量
     */
    private int nodeCount;
    /**
     * 节点列表
     */
    private CopyOnWriteArrayList<Node> nodes;
    /**
     * 连接矩阵
     */
    private double connectionMatrix[][];
    /**
     * 是否继续物理运算
     */
    private boolean doTick = false;
    private boolean doGraphicsUpdate = true;

    /**
     * 计算任务及参数列表
     */
    private List<AbstractTickEvent> tickEvents;

    private List<AbstractAnalyser> tickAnalysers;
    /**
     * 物理运算间隔，单位毫秒
     */
    private int doTickInterval = 10;
    /**
     * 终止模拟以及展示
     */
    private boolean doBreak = false;
    //以下是绘图相关参数
    private CanvasAttributes canvasAttributes;
    private MainWindow window;

    private int tickFrame;

    /**
     * 改变节点数量
     * @param targetNodeCount
     */
    public void changeNodeCount(int targetNodeCount){
        if (targetNodeCount<0){
            return;
        }

        if (targetNodeCount>nodeCount){
            for (int i = nodeCount; i < targetNodeCount; i++) {
                this.nodes.add(new Node(this, i));
            }
            double[][] targetConnectionMatrix = new double[targetNodeCount][targetNodeCount];
            for (int i = 0; i < targetNodeCount; i++) {
                for (int j = 0; j < targetNodeCount; j++) {
                    if (i<nodeCount&&j<nodeCount){
                        targetConnectionMatrix[i][j] = connectionMatrix[i][j];
                    }else{
                        targetConnectionMatrix[i][j] = 0.0;
                    }
                }

            }
            this.connectionMatrix = targetConnectionMatrix;
        }else if (targetNodeCount<nodeCount){
            for (int i = nodeCount-1; i >= targetNodeCount; i--) {
                this.nodes.remove(i);
            }
            double[][] targetConnectionMatrix = new double[targetNodeCount][targetNodeCount];
            for (int i = 0; i < targetNodeCount; i++) {
                for (int j = 0; j < targetNodeCount; j++) {
                    targetConnectionMatrix[i][j] = connectionMatrix[i][j];
                }
            }
            this.connectionMatrix = targetConnectionMatrix;
        } else {
            //if (targetNodeCount==nodeCount)
            //Do Nothing
            return;
        }
        this.nodeCount = targetNodeCount;
    }


    /**
     * 初始化网络，其中的节点位置为0，颜色为（255，0，0），连接矩阵被填充为0.0
     * @param nodeCount
     */
    public Net(int nodeCount) {
        this.tickFrame=0;
        this.nodeCount = nodeCount;
        this.nodes = new CopyOnWriteArrayList<>();
        this.tickEvents = new ArrayList<>();
        this.tickAnalysers = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            this.nodes.add(new Node(this, i));
        }
        this.connectionMatrix = new double[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                this.connectionMatrix[i][j] = 0.0;
            }
        }
    }

    /**
     * 应用初始化器，在应用前确保网络已经被正确初始化
     * @param initializers
     */
    public void applyInitializers(List<AbstractInitializer> initializers){
        for (int i = 0; i < initializers.size(); i++) {
            initializers.get(i).initiate(this);
        }
    }

    /**
     * 初始化窗口
     * @param canvasAttributes
     */
    public void initiateWindow(CanvasAttributes canvasAttributes){
        this.canvasAttributes = canvasAttributes;
        this.window = new MainWindow(this,canvasAttributes);
    }

    /**
     * 进行逐刻物理运算
     */
    public void tickPhysics(){
        long startTime = System.nanoTime();
        for (int i = 0;i<tickEvents.size();i++){
            tickEvents.get(i).tick(this);
        }
        long endTime = System.nanoTime();
        long usedTime = endTime-startTime;
        System.out.println("Current Tick Physic Time: ".concat(String.valueOf(usedTime/1000000)).concat("ms").concat(String.valueOf(usedTime%1000000)).concat("ns "));
    }

    /**
     * 进行逐刻分析器运算
     */
    public void tickAnalysers(){
        long startTime = System.nanoTime();
        for (int i = 0; i < tickAnalysers.size(); i++) {
            tickAnalysers.get(i).analyse(this);
//            for (String scoreboard:tickAnalysers.get(i).getData().keySet()){
//                Double curTickValue = tickAnalysers.get(i).getData().get(scoreboard).get(this.tickFrame);
//                System.out.println(scoreboard.concat(": ").concat(String.valueOf(curTickValue)));
//            }
        }
        long endTime = System.nanoTime();
        long usedTime = endTime-startTime;
        System.out.println("Current Tick Analyse Time: ".concat(String.valueOf(usedTime/1000000)).concat("ms").concat(String.valueOf(usedTime%1000000)).concat("ns "));

    }

    @Override
    public void run() {
        while(!doBreak){
            if (doTick){
                System.out.println("-----------------");
                System.out.println("Tick: ".concat(String.valueOf(tickFrame)));
                //进行网络仿真计算
                this.tickPhysics();
                //分析器更新自己
                this.tickAnalysers();
                System.out.println("Current Net Contain : ".concat(String.valueOf(nodeCount)).concat(" nodes."));
            }
            //绘制可视化
            if (this.window!=null){
                if (doGraphicsUpdate){
                    this.window.update();
                }
            }
            try {
                Thread.sleep(doTickInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.tickFrame+=1;
        }

    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public CopyOnWriteArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(CopyOnWriteArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public double[][] getConnectionMatrix() {
        return connectionMatrix;
    }

    public void setConnectionMatrix(double[][] connectionMatrix) {
        this.connectionMatrix = connectionMatrix;
    }

    public boolean isDoTick() {
        return doTick;
    }

    public void setDoTick(boolean doTick) {
        this.doTick = doTick;
    }

    public int getDoTickInterval() {
        return doTickInterval;
    }

    public void setDoTickInterval(int doTickInterval) {
        this.doTickInterval = doTickInterval;
    }

    public boolean isDoBreak() {
        return doBreak;
    }

    public void setDoBreak(boolean doBreak) {
        this.doBreak = doBreak;
    }

    public CanvasAttributes getCanvasAttributes() {
        return canvasAttributes;
    }

    public void setCanvasAttributes(CanvasAttributes canvasAttributes) {
        this.canvasAttributes = canvasAttributes;
    }

    public MainWindow getWindow() {
        return window;
    }

    public void setWindow(MainWindow window) {
        this.window = window;
    }

    public List<AbstractTickEvent> getTickEvents() {
        return tickEvents;
    }

    public void setTickEvents(List<AbstractTickEvent> tickEvents) {
        this.tickEvents = tickEvents;
    }

    public boolean isDoGraphicsUpdate() {
        return doGraphicsUpdate;
    }

    public void setDoGraphicsUpdate(boolean doGraphicsUpdate) {
        this.doGraphicsUpdate = doGraphicsUpdate;
    }

    public int getTickFrame() {
        return tickFrame;
    }

    public void setTickFrame(int tickFrame) {
        this.tickFrame = tickFrame;
    }

    public List<AbstractAnalyser> getTickAnalysers() {
        return tickAnalysers;
    }

    public void setTickAnalysers(List<AbstractAnalyser> tickAnalysers) {
        this.tickAnalysers = tickAnalysers;
    }

    public Net copy(){
        Net net = new Net(this.nodeCount);
        net.setNodes(new CopyOnWriteArrayList<>());
        for (int i = 0; i < nodeCount; i++) {
            net.getNodes().add(nodes.get(i).copy(net));
        }
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                net.getConnectionMatrix()[i][j] = connectionMatrix[i][j];
            }
        }
        net.doTick = false;
        net.doGraphicsUpdate = false;

        return net;
    }


}
