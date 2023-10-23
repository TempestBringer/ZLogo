package club.tempestissimo;

import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.entities.attributes.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataGraphPaintArea extends Canvas {
    private List<Double> drawData;
    /**
     * 图像缓冲区
     */
    private Image bufferedImage = null;
    private CanvasAttributes canvasAttributes;
    /**
     * y轴缩放倍率
     */
    private double yAxisMax;

    @Override
    public void paint(Graphics g){
//        System.out.println("data to draw: "+drawData.toString());
//        System.out.println("length to draw: "+drawData.size());
        int axisXStart = (int) (getWidth()*0.05);
        int axisYStart = (int) (getHeight()*0.95);
        int axisXEnd = (int) (getWidth()*0.95);
        int axisYEnd = (int) (getHeight()*0.05);
        //x轴
        g.drawLine(axisXStart, axisYStart, axisXEnd, axisYStart);
        //y轴
        g.drawLine(axisXStart, axisYStart, axisXStart, axisYEnd);
        List<Position> dots = new ArrayList<>();
        if (drawData!=null){
            for (int i = 0; i < drawData.size(); i++) {
                double dotX = getWidth()*0.05 + getWidth()*0.90*i/drawData.size();
                double dotY = getHeight()*0.95 - getHeight()*0.90*drawData.get(i)/ yAxisMax;
                dots.add(new Position(dotX,dotY,0.0));
            }
            for (int i=0;i<dots.size()-1;i++){
                int startX = (int) dots.get(i).getXPosition();
                int startY = (int) dots.get(i).getYPosition();
                int endX = (int) dots.get(i+1).getXPosition();
                int endY = (int) dots.get(i+1).getYPosition();
                g.drawLine(startX,startY,endX,endY);
            }
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

    public List<Double> getDrawData() {
        return drawData;
    }

    public void setDrawData(List<Double> drawData) {
        this.drawData = drawData;
    }

    public double getYAxisMax() {
        return yAxisMax;
    }

    public void setYAxisMax(double yAxisMax) {
        this.yAxisMax = yAxisMax;
    }
}
