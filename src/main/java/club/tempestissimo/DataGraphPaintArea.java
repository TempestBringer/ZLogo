package club.tempestissimo;

import club.tempestissimo.awt.attributes.CanvasAttributes;

import java.awt.*;
import java.util.List;

public class DataGraphPaintArea extends Canvas {
    private List<Double> drawData;
    /**
     * 图像缓冲区
     */
    private Image bufferedImage = null;
    private CanvasAttributes canvasAttributes;

    @Override
    public void paint(Graphics g){
        int axisXStart = (int) (getWidth()*0.05);
        int axisYStart = (int) (getHeight()*0.95);
        int axisXEnd = (int) (getWidth()*0.95);
        int axisYEnd = (int) (getHeight()*0.05);
        //x轴
        g.drawLine(axisXStart, axisYStart, axisXEnd, axisYStart);
        //y轴
        g.drawLine(axisXStart, axisYStart, axisXStart, axisYEnd);
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

    public List<Double> getDrawData() {
        return drawData;
    }

    public void setDrawData(List<Double> drawData) {
        this.drawData = drawData;
    }
}
