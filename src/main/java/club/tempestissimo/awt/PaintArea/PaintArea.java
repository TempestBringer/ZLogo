package club.tempestissimo.awt.PaintArea;

import club.tempestissimo.awt.MainWindow;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.Node;

import java.awt.*;

public class PaintArea extends Canvas {

    private MainWindow window;
    /**
     * 绘图数据源
     */
    private Net net;
    /**
     * 图像缓冲区
     */
    private Image bufferedImage = null;

    private CanvasAttributes canvasAttributes;
    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        for (int i = 0; i < net.getNodeCount(); i++) {
            for (int j = 0; j < net.getNodeCount(); j++){
                if (net.getConnectionMatrix()[i][j] > 0){
                    Node source = net.getNodes().get(i);
                    Node target = net.getNodes().get(j);
                    g.drawLine((int)source.getPosition().getXPosition(), (int)source.getPosition().getYPosition(),
                            (int)target.getPosition().getXPosition(), (int)target.getPosition().getYPosition());
                }
            }
        }
        for (Node node:net.getNodes()) {
            g.setColor(node.getColor().toAWTColor());
            int drawSize = node.getDrawSize();
            if (drawSize==0)
                drawSize = canvasAttributes.getDefaultDrawSize();
            g.drawOval((int)(node.getPosition().getXPosition()-canvasAttributes.getDefaultDrawSize()/2),
                    (int)(node.getPosition().getYPosition()-canvasAttributes.getDefaultDrawSize()/2),
                    drawSize, drawSize);

        }

    }

    /**
     * 更新画面
     * @param g the specified Graphics context
     */
    public void update(Graphics g){
        if(bufferedImage == null) {
            // 如果缓冲区没有创建, 则创建图片缓冲区
            bufferedImage = this.createImage(getWidth(), getHeight());
        }
        // 获取图片的绘图对象
        Graphics buffer = bufferedImage.getGraphics();
        //清空绘制区域
        buffer.clearRect(0,0, getWidth(), getHeight());
        // 向缓冲区中绘制图片
        paint(buffer);
        // 将缓冲区中的图片绘制到窗口界面中
        g.drawImage(bufferedImage, 0, 0, null);
    }


    public void setNet(Net net) {
        this.net = net;
        this.canvasAttributes =  net.getCanvasAttributes();
        this.addMouseListener(new AwtMouseListener(this));
    }

    public Net getNet() {
        return net;
    }

    public MainWindow getWindow() {
        return window;
    }

    public void setWindow(MainWindow window) {
        this.window = window;
    }
}
