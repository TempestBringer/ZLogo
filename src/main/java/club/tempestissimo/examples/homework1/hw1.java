package club.tempestissimo.examples.homework1;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.examples.homework1.initializer.SatisfactionPreferInitialize;
import club.tempestissimo.examples.homework1.tick.SAOConnectionTickEvent;
import club.tempestissimo.net.analyse.AbstractAnalyser;
import club.tempestissimo.net.analyse.DegreeDistributionAnalyser;
import club.tempestissimo.net.analyse.NetToFileAnalyser;
import club.tempestissimo.net.analyse.NodeDegreeAnalyser;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.initialize.connection.NullLinkInitializer;
import club.tempestissimo.net.initialize.place.CirclePlaceInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;
import java.util.List;

public class hw1 {
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

        //Model1的偏好
//        Preference preference = new Preference(-1.48, 1.98, 0.0, 0.0, 0.21, -0.347, 0.5, -0.33, 0.1);

//        Preference preference = new Preference(-1.78, 1.98,
//                1.0, 0.0, 0.21,
//                -0.347, 0.5,
//                -0.33, 1.0/nodeCount);
        //网络崩溃成为星形网络
//        Preference preference = new Preference(-0.48, 1.98, 1.5, 1.25, 0.21, 0.347, 0.5, -0.33, 1.0);
        //较为均衡的B0=0.23
//        Preference preference = new Preference(-1.48, 1.98,
//                0, 0,
//                0.21, -0.347,
//                0.5, -0.33,
//                0.220);
        //Model 2
//        Preference preference = new Preference(-2.10, 2.11,
//                0, 0,
//                0, -0.557,
//                0.5, 0,
//                0);

        //适用于作业2模拟新生建立朋友网络的过程
//        Preference preference = new Preference(1, -0.11,
//                -0.8, 0,
//                0, 0.15,
//                0.5, 0.2,
//                0.35);
//        Preference preference = new Preference(1.5, -1.5,
//                1.5, 0.0,
//                -1, -0.347,
//                -1, 0.33,
//                0.2);
//        Preference preference = new Preference(1.5, -1.5,
//                1.5, 0.0,
//                -1, -0.347,
//                -1, 0.33,
//                0.03);
        Preference preference = new Preference(1.5, -1.98,
                1.5, 0.0,
                -1, -0.347,
                0.5, 0.33,
                0.1);
        String baseName = "hw1";
        //初始化网络
        Net net = new Net(baseName, nodeCount);
        net.setDoTickInterval(10);
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
        net.run();
    }
}
