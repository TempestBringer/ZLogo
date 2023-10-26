package club.tempestissimo.awt;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;

import java.awt.*;

public class MainWindow {
    /**
     * 总窗口容器
     */
    private Frame frame;
    /**
     * 绘图数据源
     */
    private Net net;
    /**
     * Net转换地图
     */
    private PaintArea drawArea ;
    private String baseName;

    public void update(){
        drawArea.repaint();
    }

    public MainWindow(String baseName, Net net, CanvasAttributes canvasAttributes) {
        this.net = net;
        this.baseName = baseName;
        this.frame = new Frame(baseName);
        //创建面板
        this.drawArea = new PaintArea();
        drawArea.setNet(net);
        drawArea.setPreferredSize(new Dimension(canvasAttributes.getCanvasX(),canvasAttributes.getCanvasY()));
        frame.add(drawArea);
        frame.pack();
        frame.setVisible(true);
    }
}
