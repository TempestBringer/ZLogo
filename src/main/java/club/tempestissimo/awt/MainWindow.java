package club.tempestissimo.awt;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;

import java.awt.*;

public class MainWindow {
    /**
     * 总窗口容器
     */
    private Frame frame = new Frame("JavaAWT");
    /**
     * 绘图数据源
     */
    private Net net;
    /**
     * Net转换地图
     */
    private PaintArea drawArea ;

    public void update(){
        drawArea.repaint();
    }

    public MainWindow(Net net, CanvasAttributes canvasAttributes) {
        this.net = net;
        //创建面板
        this.drawArea = new PaintArea();
        drawArea.setNet(net);
        drawArea.setPreferredSize(new Dimension(canvasAttributes.getCanvasX(),canvasAttributes.getCanvasY()));
        frame.add(drawArea);
        frame.pack();
        frame.setVisible(true);
    }
}
