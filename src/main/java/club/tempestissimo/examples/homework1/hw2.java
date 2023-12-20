package club.tempestissimo.examples.homework1;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.examples.homework1.initializer.SatisfactionPreferInitialize;
import club.tempestissimo.examples.homework1.tick.AutoClearAllConnectionEvent;
import club.tempestissimo.examples.homework1.tick.B0ChangeEvent;
import club.tempestissimo.examples.homework1.tick.SAOConnectionTickEvent;
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
        //自拟一些运行参数
        int nodeCount = 40;
        double randomLinkInitializePossibility = 0.1;
        double randomLinkRebuildPossibility = 0.1;
        int defaultDrawSize = 10;
        int initiateRadius = 300;
        int windowWidth = 800;
        int windowHeight = 800;
        String fileOutput = "out.txt";


        Preference preference = new Preference(-1.5, -1.98,
                -1.5, 0.0,
                1, 0.347,
                0.5, 0.33,
                0.1);
        String baseName = "hw2";
        //初始化网络
        Net net = new Net(baseName, nodeCount);
        net.setDoTickInterval(0);
        List<AbstractInitializer> initializers = new ArrayList<>();
        List<AbstractTickEvent> tickEvents = new ArrayList<>();
        //1.位置初始器
        initializers.add(new CirclePlaceInitializer(initiateRadius, windowWidth/2, windowHeight/2));
//        initializers.add(new RandomPlaceInitializer(100,100,windowWidth, windowHeight));

        //2.连接初始器
//        initializers.add(new RandomLinkInitializer(0.1));
        initializers.add(new NullLinkInitializer());
        //3.偏好初始器
        initializers.add(new SatisfactionPreferInitialize(preference));

        //3.应用初始
        net.applyInitializers(initializers);

        //4.计算任务
        SAOConnectionTickEvent saoConnectionTickEvent = new SAOConnectionTickEvent();
//        saoConnectionTickEvent.useExp = true;//是否使用exp函数
        tickEvents.add(saoConnectionTickEvent);
//        tickEvents.add(new B0ChangeEvent());
        tickEvents.add(new AutoClearAllConnectionEvent());

        //5.应用计算任务
        net.setTickEvents(tickEvents);

        //6.准备分析器
        List<AbstractAnalyser> tickAnalysers = new ArrayList<>();
        tickAnalysers.add(new NodeDegreeAnalyser(baseName));
//        tickAnalysers.add(new NetToFileAnalyser(fileOutput));
        tickAnalysers.add(new DegreeDistributionAnalyser(baseName));
        net.setTickAnalysers(tickAnalysers);

        //7.可视化
        CanvasAttributes canvasAttributes = new CanvasAttributes(windowWidth,windowHeight,defaultDrawSize);
        net.initiateWindow(canvasAttributes);

        //8.步进
        net.setDoTick(true);
        net.setDoAnalyse(true);

        //9.开始执行
        net.start();

        // 等待运行完毕
        while(net.isDoTick()){
            System.out.println("Checking:".concat(String.valueOf(net.isDoTick())));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Saving Data:");
        //取数据
        List<Double> total_degree = net.getTickAnalysers().get(0).getData().get(net.getTickAnalysers().get(0).getData().keySet().toArray()[1]);
        List<Double> max_degree = net.getTickAnalysers().get(0).getData().get(net.getTickAnalysers().get(0).getData().keySet().toArray()[0]);
        //写数据
        try{
            String folderPath = "./generated/"; // 文件夹路径
            File dir = new File(folderPath);
            if (!dir.exists()){
                boolean createResult = dir.mkdir();
            }
            String filePath = "./generated/matlab_".concat(String.valueOf(System.currentTimeMillis())).concat(".m");
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(filePath,true),"UTF-8"));
            writer.write("clear;\n");
            writer.write("clc;\n");
            writer.write("close all;\n");
            writer.write("nodeCount=".concat(String.valueOf(nodeCount)).concat(";"));
            writer.write("\n");

            String totalDegreeData = "totalDegreeData=";
            totalDegreeData = totalDegreeData.concat(total_degree.toString());
            totalDegreeData = totalDegreeData.concat(";\n");
            writer.write(totalDegreeData);

            String maxDegreeData = "maxDegreeData=";
            maxDegreeData = maxDegreeData.concat(max_degree.toString());
            maxDegreeData = maxDegreeData.concat(";\n");
            writer.write(maxDegreeData);

            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("写入完毕");
    }
}
