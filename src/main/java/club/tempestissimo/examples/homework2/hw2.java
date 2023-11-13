package club.tempestissimo.examples.homework2;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.analyse.AbstractAnalyser;
import club.tempestissimo.net.analyse.DegreeDistributionAnalyser;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.initialize.connection.NullLinkInitializer;
import club.tempestissimo.net.initialize.place.CirclePlaceInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;

import java.util.ArrayList;
import java.util.List;

public class hw2 {
    public static void main(String[] args) {
        //网络节点数设置
        int nodeCount = 100;
        //备注
        String comment = "更改的变量是Popularity";
        String baseName = "hw2";
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
        //0.初始化网络节点
        Net net = new Net(baseName,nodeCount);
        //1.替换网络初始偏好参数

        //2.装入初始化方法
        List<AbstractInitializer> initializers = new ArrayList<>();
        //初始化位置
        initializers.add(new CirclePlaceInitializer(initiateRadius, windowWidth/2, windowHeight/2));
        //不初始化任何初始链接
        initializers.add(new NullLinkInitializer());
        //初始化节点偏好权重
//    initializers.add(new SatisfactionPreferInitialize(newPreference));
        //应用初始化方法
        net.applyInitializers(initializers);
        //3.装入每刻计算任务
        List<AbstractTickEvent> tickEvents = new ArrayList<>();

        //应用计算任务
        net.setTickEvents(tickEvents);
        //4.装入分析器
        List<AbstractAnalyser> tickAnalysers = new ArrayList<>();
//        tickAnalysers.add(new DegreeDistributionAnalyser("B0=".concat(String.valueOf(arg))));
        net.setTickAnalysers(tickAnalysers);
        //5.应用可视化
        if (doInitiateWindow){
            CanvasAttributes canvasAttributes = new CanvasAttributes(windowWidth, windowHeight, defaultDrawSize);
            net.initiateWindow(canvasAttributes);
            net.setDoGraphicsUpdate(true);
        }
        //6.关闭性能监测以及当前刻输出
        net.setDoLogCurrentTick(false);
        net.setDoLogPerformance(false);
        net.setDoTick(true);
        net.start();
    }



}
