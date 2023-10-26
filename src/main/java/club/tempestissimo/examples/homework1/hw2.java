package club.tempestissimo.examples.homework1;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.examples.homework1.initializer.SatisfactionPreferInitialize;
import club.tempestissimo.examples.homework1.tick.SAOConnectionTickEvent;
import club.tempestissimo.examples.homework1.tick.TickStopEvent;
import club.tempestissimo.net.analyse.AbstractAnalyser;
import club.tempestissimo.net.analyse.DegreeDistributionAnalyser;
import club.tempestissimo.net.analyse.NodeDegreeAnalyser;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.initialize.connection.NullLinkInitializer;
import club.tempestissimo.net.initialize.place.CirclePlaceInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hw2 {
    public static void main(String[] args) {
        //网络节点数设置
        int nodeCount = 100;
        //主窗体大小设置
        int defaultDrawSize = 10;
        int initiateRadius = 300;
        int windowWidth = 800;
        int windowHeight = 800;
        //B0参数搜索范围
        double argStart = 0.00;
        double argStep = 0.005;
        double argStop = 0.50;
        //模拟步数
        int tickSteps = 3000;
        //是否为每个网络提供运行中的网络可视化
        boolean doInitiateWindow = false;
        //基准偏好权重
        Preference basePreference = new Preference(1.5, -1.5,
                1.5, 0.0,
                -1, -0.347,
                -1, 0.33,
                0.2);
        List<Net> nets = new ArrayList<Net>();

        for(double arg = argStart;arg<argStop;arg+=argStep){
            String baseName = "B0 = ".concat(String.valueOf(arg));
            //0.初始化网络节点
            Net net = new Net(baseName,nodeCount);
            //1.替换网络初始偏好参数
            Preference newPreference = basePreference.copy();
            newPreference.setBalancePreferenceB0(arg);
            //2.装入初始化方法
            List<AbstractInitializer> initializers = new ArrayList<>();
            //初始化位置
            initializers.add(new CirclePlaceInitializer(initiateRadius, windowWidth/2, windowHeight/2));
            //不初始化任何初始链接
            initializers.add(new NullLinkInitializer());
            //初始化节点偏好权重
            initializers.add(new SatisfactionPreferInitialize(newPreference));
            //应用初始化方法
            net.applyInitializers(initializers);
            //3.装入每刻计算任务
            List<AbstractTickEvent> tickEvents = new ArrayList<>();
            SAOConnectionTickEvent saoConnectionTickEvent = new SAOConnectionTickEvent();
            tickEvents.add(saoConnectionTickEvent);
            tickEvents.add(new TickStopEvent(tickSteps));
            //应用计算任务
            net.setTickEvents(tickEvents);
            //4.装入分析器
            List<AbstractAnalyser> tickAnalysers = new ArrayList<>();
//            tickAnalysers.add(new NodeDegreeAnalyser("B0=".concat(String.valueOf(arg))));
            tickAnalysers.add(new DegreeDistributionAnalyser("B0=".concat(String.valueOf(arg))));
            net.setTickAnalysers(tickAnalysers);
            //5.应用可视化
            if (doInitiateWindow) {
                CanvasAttributes canvasAttributes = new CanvasAttributes(windowWidth,windowHeight,defaultDrawSize);
                net.initiateWindow(canvasAttributes);
                net.setDoGraphicsUpdate(true);
            }
            //6.关闭性能监测以及当前刻输出
            net.setDoLogCurrentTick(false);
            net.setDoLogPerformance(false);
            //提交到作业集合
            nets.add(net);
        }
        //异步并行模拟运算
        for (int i = 0; i < nets.size(); i++) {
            nets.get(i).setDoTick(true);
            nets.get(i).start();
        }
        //准备停止以及收集数据
        boolean allDone = true;
        while(true){
            allDone = true;
            for (int i = 0; i < nets.size(); i++) {
                if (nets.get(i).isDoTick()){

                    allDone = false;
                    break;
                }
            }
            if (allDone){
                System.out.println("Simulation Done");
                break;
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //全部迭代完毕准备分析
        for (int i = 0; i < nets.size(); i++) {
            HashMap<String, List<Double>> analyserResultMapping =  nets.get(i).getTickAnalysers().get(0).getData();
            String[] strings = analyserResultMapping.keySet().toArray(new String[analyserResultMapping.keySet().size()]);
            List<Double> doubles = analyserResultMapping.get(strings[0]);
            System.out.print(doubles);
            System.out.println(";");
        }
//        List<Double> perB0MaxDegreeNodeCount = new ArrayList<>();
//        List<Double> perB0SumDegree = new ArrayList<>();
//        List<Double> perB0SumDegree49 = new ArrayList<>();
//        List<Double> perB0SumDegree48 = new ArrayList<>();
//        List<Double> perB0SumDegree47 = new ArrayList<>();
//        List<Double> perB0SumDegree46 = new ArrayList<>();
//        List<Double> perB0SumDegree45 = new ArrayList<>();
//        for (int i = 0; i < nets.size(); i++) {
//
//            HashMap<String, List<Double>> analyserResultMapping =  nets.get(i).getTickAnalysers().get(0).getData();
//            String[] strings = analyserResultMapping.keySet().toArray(new String[analyserResultMapping.keySet().size()]);
//            List<Double> doubles = analyserResultMapping.get(strings[0]);
//            perB0MaxDegreeNodeCount.add(doubles.get(doubles.size()-1));
//            perB0SumDegree49.add(doubles.get(doubles.size()-2));
//            perB0SumDegree48.add(doubles.get(doubles.size()-3));
//            perB0SumDegree47.add(doubles.get(doubles.size()-4));
//            perB0SumDegree46.add(doubles.get(doubles.size()-5));
//            perB0SumDegree45.add(doubles.get(doubles.size()-6));
//
//            double sumDegree = 0;
//            for (int j = 0; j < doubles.size(); j++) {
//                sumDegree+=j*doubles.get(j);
//            }
//            perB0SumDegree.add((double) sumDegree);
//        }
//        System.out.println("Degree=50 Count:"+perB0MaxDegreeNodeCount.toString());
//        System.out.println("Degree=49 Count:"+perB0SumDegree49.toString());
//        System.out.println("Degree=48 Count:"+perB0SumDegree48.toString());
//        System.out.println("Degree=47 Count:"+perB0SumDegree47.toString());
//        System.out.println("Degree=46 Count:"+perB0SumDegree46.toString());
//        System.out.println("Degree=45 Count:"+perB0SumDegree45.toString());
//        System.out.println("Total Degree:"+perB0SumDegree.toString());

    }
}
