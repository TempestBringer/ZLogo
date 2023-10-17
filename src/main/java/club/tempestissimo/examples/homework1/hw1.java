package club.tempestissimo.examples.homework1;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.examples.homework1.initializer.SatisfactionPreferInitialize;
import club.tempestissimo.examples.homework1.tick.SAOConnectionTickEvent;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.initialize.connection.NullLinkInitializer;
import club.tempestissimo.net.initialize.connection.RandomLinkInitializer;
import club.tempestissimo.net.initialize.place.CirclePlaceInitializer;
import club.tempestissimo.net.initialize.place.RandomPlaceInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;
import club.tempestissimo.net.tick.connection.RandomConnectionBuildTickEvent;
import club.tempestissimo.net.tick.node.NodeCountChangeTickEvent;
import club.tempestissimo.net.tick.place.CirclePlaceTickEvent;

import java.util.ArrayList;
import java.util.List;

public class hw1 {
    public static void main(String[] args) {
        //自拟一些运行参数
        int nodeCount = 20;
        double randomLinkInitializePossibility = 0.1;
        double randomLinkRebuildPossibility = 0.1;
        int defaultDrawSize = 20;
        int initiateRadius = 300;
        int windowWidth = 1440;
        int windowHeight = 960;

        //Model1的偏好
//        Preference preference = new Preference(-1.48, 1.98, 0.3, 0.25, 0.21, -0.347, 1, -0.33, 1.0);
        //网络崩溃成为星形网络
//        Preference preference = new Preference(-0.48, 1.98, 1.5, 1.25, 0.21, 0.347, 0.5, -0.33, 1.0);
        //较为均衡的B0=0.23
//        Preference preference = new Preference(-1.48, 1.98,
//                0, 0,
//                0.21, -0.347,
//                0.5, -0.33,
//                0.225);
        //Model 2
//        Preference preference = new Preference(-2.10, 2.11,
//                0, 0,
//                0, -0.557,
//                0.5, 0,
//                0);
        Preference preference = new Preference(-2.10, 2.11,
                0, 0,
                0, -0.557,
                0.5, 0,
                0);

        //初始化网络
        Net net = new Net(nodeCount);
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
        tickEvents.add(new SAOConnectionTickEvent());

        //5.应用计算任务
        net.setTickEvents(tickEvents);

        //6.可视化
        CanvasAttributes canvasAttributes = new CanvasAttributes(windowWidth,windowHeight,defaultDrawSize);
        net.initiateWindow(canvasAttributes);

        //7.步进
        net.setDoTick(true);

        //8.开始执行
        net.run();
    }
}
