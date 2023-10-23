package club.tempestissimo.awt;

import club.tempestissimo.DataGraphPaintArea;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.net.analyse.AbstractAnalyser;

import java.awt.*;
import java.util.List;

public class DataGraphWindow {

    private Frame frame;
    private List<Double> drawData;
    private DataGraphPaintArea dataGraphPaintArea;
    private double yAxisMax;

    public void update(){
        dataGraphPaintArea.setDrawData(this.drawData);
        dataGraphPaintArea.repaint();
    }

    public DataGraphWindow(String windowName, CanvasAttributes canvasAttributes, double yAxisMax){
        this.frame = new Frame(windowName);
        //创建面板
        this.dataGraphPaintArea = new DataGraphPaintArea();
        this.yAxisMax = yAxisMax;
        this.dataGraphPaintArea.setYAxisMax(this.yAxisMax);
        dataGraphPaintArea.setDrawData(drawData);
        dataGraphPaintArea.setPreferredSize(new Dimension(canvasAttributes.getCanvasX(),canvasAttributes.getCanvasY()));
        frame.add(dataGraphPaintArea);
        frame.pack();
        frame.setVisible(true);
    }

    public List<Double> getDrawData() {
        return drawData;
    }

    public void setDrawData(List<Double> drawData) {
        this.drawData = drawData;
    }
}
