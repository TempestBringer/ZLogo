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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hw2 {
    public static void main(String[] args) {
        //网络节点数设置
        int nodeCount = 100;
        //备注
        String comment = "更改的变量是Popularity";
        //主窗体大小设置
        int defaultDrawSize = 10;
        int initiateRadius = 300;
        int windowWidth = 800;
        int windowHeight = 800;
        //B0参数搜索范围
        double argStart = -1.0;
        double argStep = 0.05;
        double argStop = 3.0;
        //模拟步数
        int tickSteps = 4000;
        //是否为每个网络提供运行中的网络可视化
        boolean doInitiateWindow = false;
        //基准偏好权重
        Preference basePreference = new Preference(1.5, -1.5,
                1.5, 0.0,
                -1, -0.347,
                -1, 0.33,
                0.2);
        List<Net> nets = new ArrayList<Net>();
        List<Double> variables = new ArrayList<>();
        for(double arg = argStart;arg<argStop;arg+=argStep){
            variables.add(arg);
            String baseName = "B0 = ".concat(String.valueOf(arg));
            //0.初始化网络节点
            Net net = new Net(baseName,nodeCount);
            //1.替换网络初始偏好参数
            Preference newPreference = basePreference.copy();
//            newPreference.setBalancePreferenceB0(arg);
            newPreference.setPopularityPreference(arg);
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
        //全部迭代完毕准备分析以及转储
        try {
            String filePath = "./generated/matlab_".concat(String.valueOf(System.currentTimeMillis())).concat(".m");
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(filePath,true),"UTF-8"));
            writer.write("clear;\n");
            writer.write("clc;\n");
            writer.write("close all;\n");

            writer.write("nodeCount=".concat(String.valueOf(nodeCount)).concat(";"));
            writer.write("\n");
            writer.write("% ".concat(comment).concat(";"));
            writer.write("\n");
            writer.write("argStart=".concat(String.valueOf(argStart)).concat(";"));
            writer.write("\n");
            writer.write("argStep=".concat(String.valueOf(argStep)).concat(";"));
            writer.write("\n");
            writer.write("argStop=".concat(String.valueOf(argStop)).concat(";"));
            writer.write("\n");
            writer.write(basePreference.toString());
            writer.write("\n");
            writer.write("x=[0:nodeCount-1];\n");
//            writer.write("y=[argStart:argStep:argStop-argStep];\n");
            writer.write("y=[");
            for (int i = 0; i < nets.size(); i++) {
                writer.write(String.valueOf(variables.get(i)));
                if (i!= nets.size()-1)
                    writer.write(",");
            }
            writer.write("];\n");
            writer.write("[X,Y]=meshgrid(x,y);\n");
            String data = "z=[";
            for (int i = 0; i < nets.size(); i++) {
                HashMap<String, List<Double>> analyserResultMapping =  nets.get(i).getTickAnalysers().get(0).getData();
                String[] strings = analyserResultMapping.keySet().toArray(new String[analyserResultMapping.keySet().size()]);
                List<Double> doubles = analyserResultMapping.get(strings[0]);
                System.out.print(doubles);
                data = data.concat(doubles.toString());
                if (i!=nets.size()-1) {
                    data = data.concat(";");
                }
                System.out.println(";");
                //文件转储


            }
            data = data.concat("]\n");
            writer.write(data);
            writer.write("figure\n");
            writer.write("plot3(X,Y,z);\n");
            writer.write("figure;\n");
            writer.write("surf(X,Y,z);\n");


            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
