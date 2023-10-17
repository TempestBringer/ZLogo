package club.tempestissimo.net.entities;

import club.tempestissimo.net.entities.attributes.Color;
import club.tempestissimo.net.entities.attributes.Position;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.utils.Satisfaction;

import java.util.HashMap;

public class Node {
    /**
     * 自指归属Net
     */
    private Net net;
    /**
     * 节点序号
     */
    private int index;
    /**
     * 位置
     */
    private Position position;
    /**
     * 对各种连边的喜好程度
     */
    private Preference preference;
    /**
     * 绘制原型大小
     */
    private int drawSize;
    /**
     * 颜色
     */
    private Color color;
    /**
     * 节点的其他属性
     */
    private HashMap<String, Double> args;

    public double calculateSatisfaction(){
        if (!net.getNodes().contains(this)){
            System.out.println("不在网络中的节点");
            return 0.0;
        }
        double sum=0;
        int index = net.getNodes().indexOf(this);
        double[][] connectionMatrix = net.getConnectionMatrix();
//        for (int i = 0; i < net.getNodeCount(); i++) {
            sum+=preference.getDensityPreference()* Satisfaction.getSatisfactionOfDensity(this);
            sum+=preference.getReciprocityPreference()* Satisfaction.getSatisfactionOfReciprocity(this);
            sum+=preference.getPopularityPreference()* Satisfaction.getSatisfactionOfPopularity(this);
            sum+=preference.getActivityPreference()* Satisfaction.getSatisfactionOfActivity(this);
            sum+=preference.getTransitivityPreference()* Satisfaction.getSatisfactionOfTransitivity(this);
            sum+=preference.getIndirectRelationEffectPreference()* Satisfaction.getSatisfactionOfIndirectRelationsEffect(this, preference.getIndirectRelationEffectPreferenceIsWeakConnectionThreshold());
            sum+=preference.getBalancePreference()* Satisfaction.getSatisfactionOfBalance(this, preference.getBalancePreferenceB0());

//        }
        return sum;
    }

    public Node(Net net, int index) {
        this.net = net;
        this.index = index;
        this.position = new Position(0,0,0);
        this.drawSize = 0;
        this.color = new Color(255,0,0);
        this.args = new HashMap<>();
    }


    public Node(Net net, int index, Position position, int drawSize, Color color) {
        this.net = net;
        this.index = index;
        this.position = position;
        this.drawSize = drawSize;
        this.color = color;
        this.args = new HashMap<>();
    }

    public Net getNet() {
        return net;
    }

    public void setNet(Net net) {
        this.net = net;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDrawSize() {
        return drawSize;
    }

    public void setDrawSize(int drawSize) {
        this.drawSize = drawSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public HashMap<String, Double> getArgs() {
        return args;
    }

    public void setArgs(HashMap<String, Double> args) {
        this.args = args;
    }

}
