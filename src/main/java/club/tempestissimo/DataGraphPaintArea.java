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


//    public void Paint(Graphics g){
////        System.out.println("data to draw: "+drawData.toString());
////        System.out.println("length to draw: "+drawData.size());
//        int axisXStart = (int) (getWidth()*0.05);
//        int axisYStart = (int) (getHeight()*0.95);
//        int axisXEnd = (int) (getWidth()*0.95);
//        int axisYEnd = (int) (getHeight()*0.05);
//
//        //x轴
//        g.drawLine(axisXStart, axisYStart, axisXEnd, axisYStart);
//        //y轴
//        g.drawLine(axisXStart, axisYStart, axisXStart, axisYEnd);
//        List<Position> dots = new ArrayList<>();
//        if (drawData!=null){
//            for (int i = 0; i < drawData.size(); i++) {
//                double dotX = getWidth()*0.05 + getWidth()*0.90*i/drawData.size();
//                double dotY = getHeight()*0.95 - getHeight()*0.90*drawData.get(i)/ yAxisMax;
//                dots.add(new Position(dotX,dotY,0.0));
//            }
//            for (int i=0;i<dots.size()-1;i++){
//                int startX = (int) dots.get(i).getXPosition();
//                int startY = (int) dots.get(i).getYPosition();
//                int endX = (int) dots.get(i+1).getXPosition();
//                int endY = (int) dots.get(i+1).getYPosition();
//                g.drawLine(startX,startY,endX,endY);
//            }
//        }
//    }
    @Override
    public void paint(Graphics g){
        if (this.drawData==null)
            return;
        //遍历数据取出y轴上下限
        double minYValue = -1;
        double maxYValue = 1;
        double minXValue = 0;
        double maxXValue = this.drawData.size();
        if (maxXValue<=0)
            return;
        for (int i = 0; i < drawData.size(); i++) {
            double readingData = drawData.get(i);
            if (readingData<minYValue){
                minYValue = readingData;
            }
            if (readingData>maxYValue){
                maxYValue = readingData;
            }
        }

        //绘制坐标
        Position originPoint = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,0.0,0.0);
        Position xMax = originPoint.copy();
        xMax.setXPosition(getWidth()*0.95);
        Position yMin = originPoint.copy();
        yMin.setYPosition(getHeight()*0.95);
        Position yMax = originPoint.copy();
        yMax.setYPosition(getHeight()*0.05);
        g.drawLine((int) originPoint.getXPosition(), (int) originPoint.getYPosition(),
                (int) xMax.getXPosition(), (int) xMax.getYPosition());
        g.drawLine((int) yMin.getXPosition(), (int) yMin.getYPosition(),
                (int) yMax.getXPosition(), (int) yMax.getYPosition());
        //绘制刻度线
        double xAxisValueInterval = this.getAxisInterval(maxXValue-minXValue);
        double yAxisValueInterval = this.getAxisInterval(maxYValue-minYValue);
        //x轴刻度线
        for (int i = 0; i < maxXValue; i+=xAxisValueInterval) {
            Position from = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,i,0.0);
            Position to = from.copy();
            to.setYPosition(to.getYPosition()+getHeight()*0.05);
            g.drawLine((int) from.getXPosition(), (int) from.getYPosition(),
                    (int) to.getXPosition(), (int) to.getYPosition());

            g.drawString(String.valueOf(i), (int) to.getXPosition(), (int) to.getYPosition());
        }
        for (int i = 0; i > minXValue; i-=xAxisValueInterval) {
            Position from = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,i,0.0);
            Position to = from.copy();
            to.setYPosition(to.getYPosition()+getHeight()*0.05);
            g.drawLine((int) from.getXPosition(), (int) from.getYPosition(),
                    (int) to.getXPosition(), (int) to.getYPosition());

            g.drawString(String.valueOf(i), (int) to.getXPosition(), (int) to.getYPosition());
        }
        //y轴刻度线
        for (int i = 0; i < maxYValue; i+=yAxisValueInterval) {
            Position from = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,0,i);
            Position to = from.copy();
            to.setXPosition(to.getXPosition()-getWidth()*0.05);
            g.drawLine((int) from.getXPosition(), (int) from.getYPosition(),
                    (int) to.getXPosition(), (int) to.getYPosition());
            g.drawString(String.valueOf(i), (int) to.getXPosition(), (int) to.getYPosition());
        }
        for (int i = 0; i > minYValue; i-=yAxisValueInterval) {
            Position from = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,0,i);
            Position to = from.copy();
            to.setXPosition(to.getXPosition()-getWidth()*0.05);
            g.drawLine((int) from.getXPosition(), (int) from.getYPosition(),
                    (int) to.getXPosition(), (int) to.getYPosition());
            g.drawString(String.valueOf(i), (int) to.getXPosition(), (int) to.getYPosition());
        }

        //转换点坐标
        List<Position> dots = new ArrayList<>();
        for (int i = 0; i < drawData.size(); i++) {
            Position dot = dataToPosition(minXValue,maxXValue,minYValue,maxYValue,i, drawData.get(i));
            dots.add(dot);
        }
        for (int i = 0; i < dots.size() - 1; i++) {
            g.drawLine((int) dots.get(i).getXPosition(), (int) dots.get(i).getYPosition(),
                    (int) dots.get(i+1).getXPosition(), (int) dots.get(i+1).getYPosition());
        }

    }

    private Position dataToPosition(double xMin, double xMax, double yMin, double yMax, double x, double y){
        double yRatio = (y - yMin)/(yMax - yMin);
        double xRatio = (x - xMin)/(xMax - xMin);
        double xOnGraph = getWidth()*0.9*xRatio + getWidth()*0.05;
        double yOnGraph = getHeight()*0.95 - getHeight()*0.9*yRatio;
        return new Position(xOnGraph, yOnGraph, 0);

    }


    /**
     * 根据y取值上下限生成刻度，为1x10^n，要求n比input低一数量级
     * 比如上下限差值27，则n=1
     * @param input
     * @return
     */
    private double getAxisInterval(double input){
        int nMin = -10;
        int nMax = 10;
        for (int i = nMin; i < nMax; i++) {
            if (input%Math.pow(10,i)==input)
                return Math.pow(10,i-1);
        }
        return Math.pow(10,10);
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
