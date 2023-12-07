package club.tempestissimo.awt;

import club.tempestissimo.awt.PaintArea.PaintArea;
import club.tempestissimo.awt.attributes.CanvasAttributes;
import club.tempestissimo.awt.buttons.*;
import club.tempestissimo.net.entities.Net;

import javax.swing.*;
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
    /**
     * 控制面板
     */
    private Frame controlFrame;
    private Panel controlPanel;
    private Button startTickButton = new Button("Do Tick");
    private Button stopTickButton = new Button("No Tick");
    private Button startAnalyseButton = new Button("Do Analyse");
    private Button stopAnalyseButton = new Button("No Analyse");
    private Button clearAnalyseButton = new Button("Clear Analyse");
    private TextField tickInterval = new TextField();
    private Button submitTickIntervalButton = new Button("Submit Tick Interval");
    //节点参数

    private TextField nodeIndex = new TextField();
    private TextField nodeDensityPreferenceTextField = new TextField();
    private TextField nodeReciprocityPreferenceTextField = new TextField();
    private TextField nodePopularityPreference = new TextField();
    private TextField nodeActivityPreferenceTextField = new TextField();
    private TextField nodeTransitivityPreferenceTextField = new TextField();
    private TextField nodeIndirectRelationEffectPreferenceTextField = new TextField();
    private TextField nodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField = new TextField();
    private TextField nodeBalancePreferenceTextField = new TextField();
    private TextField nodeBalancePreferenceB0TextField = new TextField();
    private Button submitNodePreferenceButton = new Button("Submit Node Preference");
    private Button submitNodePreferenceToAllButton = new Button("Submit Node Preference To All");
    private TextField submitNodePreferenceIndexStartTextField = new TextField();
    private TextField submitNodePreferenceIndexEndTextField = new TextField();
    private Button submitNodePreferenceToSegmentButton = new Button("Submit Node Preference To Segment");

    /**
     *
     */
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
        drawArea.setWindow(this);
        drawArea.setPreferredSize(new Dimension(canvasAttributes.getCanvasX(),canvasAttributes.getCanvasY()));
        frame.add(drawArea);
        frame.pack();
        frame.setVisible(true);
        //控制面板
        this.controlFrame = new Frame(baseName);
        this.controlPanel = new Panel();
        this.startTickButton.addMouseListener(new NetDoTickButton(this.startTickButton, this));
        this.stopTickButton.addMouseListener(new NetNoTickButton(this.stopTickButton, this));
        this.controlPanel.add(startTickButton);
        this.controlPanel.add(stopTickButton);
        this.startAnalyseButton.addMouseListener(new NetDoAnalyseButton(this.startAnalyseButton, this));
        this.stopAnalyseButton.addMouseListener(new NetNoAnalyseButton(this.stopAnalyseButton, this));
        this.clearAnalyseButton.addMouseListener(new NetClearAnalyseButton(this.clearAnalyseButton, this));
        this.controlPanel.add(startAnalyseButton);
        this.controlPanel.add(stopAnalyseButton);
        this.controlPanel.add(clearAnalyseButton);
        this.controlPanel.add(tickInterval);
        this.submitTickIntervalButton.addMouseListener(new SubmitTickIntervalButton(this.submitTickIntervalButton, this));
        this.controlPanel.add(submitTickIntervalButton);

        this.controlPanel.add(new Label("Node Related"));
        this.controlPanel.add(new Label("0.Index:"));
        this.controlPanel.add(nodeIndex);
        this.controlPanel.add(new Label("1.Density:"));
        this.controlPanel.add(nodeDensityPreferenceTextField);
        this.controlPanel.add(new Label("2.Reciprocity:"));
        this.controlPanel.add(nodeReciprocityPreferenceTextField);
        this.controlPanel.add(new Label("3.Popularity:"));
        this.controlPanel.add(nodePopularityPreference);
        this.controlPanel.add(new Label("4.Activity:"));
        this.controlPanel.add(nodeActivityPreferenceTextField);
        this.controlPanel.add(new Label("5.Transitivity:"));
        this.controlPanel.add(nodeTransitivityPreferenceTextField);
        this.controlPanel.add(new Label("6.IndirectRelation:"));
        this.controlPanel.add(nodeIndirectRelationEffectPreferenceTextField);
        this.controlPanel.add(new Label("7.IndirectRelationThreshold:"));
        this.controlPanel.add(nodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField);
        this.controlPanel.add(new Label("8.Balance:"));
        this.controlPanel.add(nodeBalancePreferenceTextField);
        this.controlPanel.add(new Label("9.BalanceB0:"));
        this.controlPanel.add(nodeBalancePreferenceB0TextField);
        this.submitNodePreferenceButton.addMouseListener(new SubmitNodePreferenceButton(this.submitNodePreferenceButton, this));
        this.controlPanel.add(submitNodePreferenceButton);
        this.submitNodePreferenceToAllButton.addMouseListener(new SubmitNodePreferenceToAllButton(this.submitNodePreferenceToAllButton, this));
        this.controlPanel.add(submitNodePreferenceToAllButton);

        this.controlPanel.add(new Label("ApplyIndex.Start:"));
        this.controlPanel.add(submitNodePreferenceIndexStartTextField);
        this.controlPanel.add(new Label("ApplyIndex.End:"));
        this.controlPanel.add(submitNodePreferenceIndexEndTextField);
        this.submitNodePreferenceToSegmentButton.addMouseListener(new SubmitNodePreferenceToSegmentButton(this.submitNodePreferenceToSegmentButton, this));
        this.controlPanel.add(submitNodePreferenceToSegmentButton);

        this.controlFrame.add(this.controlPanel);
        this.controlFrame.pack();
        this.controlFrame.setVisible(true);
    }

    public Net getNet() {
        return net;
    }

    public Frame getFrame() {
        return frame;
    }

    public PaintArea getDrawArea() {
        return drawArea;
    }

    public Frame getControlFrame() {
        return controlFrame;
    }

    public Panel getControlPanel() {
        return controlPanel;
    }

    public Button getStartTickButton() {
        return startTickButton;
    }

    public Button getStopTickButton() {
        return stopTickButton;
    }

    public TextField getTickInterval() {
        return tickInterval;
    }

    public Button getSubmitTickIntervalButton() {
        return submitTickIntervalButton;
    }

    public String getBaseName() {
        return baseName;
    }

    public Button getStartAnalyseButton() {
        return startAnalyseButton;
    }

    public Button getStopAnalyseButton() {
        return stopAnalyseButton;
    }

    public Button getClearAnalyseButton() {
        return clearAnalyseButton;
    }

    public TextField getNodeDensityPreferenceTextField() {
        return nodeDensityPreferenceTextField;
    }

    public TextField getNodeReciprocityPreferenceTextField() {
        return nodeReciprocityPreferenceTextField;
    }

    public TextField getNodePopularityPreference() {
        return nodePopularityPreference;
    }

    public TextField getNodeActivityPreferenceTextField() {
        return nodeActivityPreferenceTextField;
    }

    public TextField getNodeTransitivityPreferenceTextField() {
        return nodeTransitivityPreferenceTextField;
    }

    public TextField getNodeIndirectRelationEffectPreferenceTextField() {
        return nodeIndirectRelationEffectPreferenceTextField;
    }

    public TextField getNodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField() {
        return nodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField;
    }

    public TextField getNodeBalancePreferenceTextField() {
        return nodeBalancePreferenceTextField;
    }

    public TextField getNodeBalancePreferenceB0TextField() {
        return nodeBalancePreferenceB0TextField;
    }

    public Button getSubmitNodePreferenceButton() {
        return submitNodePreferenceButton;
    }

    public TextField getSubmitNodePreferenceIndexStartTextField() {
        return submitNodePreferenceIndexStartTextField;
    }

    public TextField getSubmitNodePreferenceIndexEndTextField() {
        return submitNodePreferenceIndexEndTextField;
    }


    public TextField getNodeIndex() {
        return nodeIndex;
    }
}
