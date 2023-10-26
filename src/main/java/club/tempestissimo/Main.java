package club.tempestissimo;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;
import club.tempestissimo.net.initialize.connection.RandomLinkInitializer;
import club.tempestissimo.net.initialize.place.CirclePlaceInitializer;
import club.tempestissimo.net.tick.AbstractTickEvent;
import club.tempestissimo.net.tick.connection.RandomConnectionBuildTickEvent;
import club.tempestissimo.net.tick.node.NodeCountChangeTickEvent;
import club.tempestissimo.net.tick.place.CirclePlaceTickEvent;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //自拟一些运行参数
        int nodeCount = 10;
        double randomLinkInitializePossibility = 0.1;
        double randomLinkRebuildPossibility = 0.1;
        int defaultDrawSize = 20;
        int initiateRadius = 300;
        int windowWidth = 1440;
        int windowHeight = 960;
        int windowWidthPadding = 160;
        int windowHeightPadding = 90;
        //Model1的偏好
        Preference preference = new Preference(-1.48, 1.98, 1.0, 1.0, 0.21, -0.347, 0.5, -0.33, 1.0);

        String baseName = "main";
        //初始化网络
        Net net = new Net(baseName, nodeCount);
        net.setDoTickInterval(1000);
        List<AbstractInitializer> initializers = new ArrayList<>();
        List<AbstractTickEvent> tickEvents = new ArrayList<>();
        //1.位置初始器
        initializers.add(new CirclePlaceInitializer(initiateRadius, windowWidth/2, windowHeight/2));

        //2.连接初始器
        initializers.add(new RandomLinkInitializer(randomLinkInitializePossibility));

        //3.应用初始
        net.applyInitializers(initializers);

        //4.计算任务
        tickEvents.add(new RandomConnectionBuildTickEvent(randomLinkRebuildPossibility));
        tickEvents.add(new CirclePlaceTickEvent(initiateRadius, windowWidth/2, windowHeight/2));
        tickEvents.add(new NodeCountChangeTickEvent());

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