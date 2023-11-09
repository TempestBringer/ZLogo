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
    private boolean doLog = false;

    /**
     * 读取节点的内生参数，如无参数会返回0.0
     * @param entry
     * @return
     */
    public double getArg(String entry){
        if (args.containsKey(entry)){
            return args.get(entry);
        }else{
            return 0.0;
        }
    }

    /**
     * 设置节点的内生参数
     * @param entry 参数名
     * @param value 值
     */
    public void setArg(String entry, double value){
        args.put(entry, value);
    }

    /**
     * 计算节点的满意度，用于作业1
     * @return
     */
    public double calculateSatisfaction(){
        if (!net.getNodes().contains(this)){
            System.out.println("不在网络中的节点");
            return 0.0;
        }
        double sum=0;
        int index = net.getNodes().indexOf(this);
        double[][] connectionMatrix = net.getConnectionMatrix();
        double densitySatisfaction= preference.getDensityPreference()* Satisfaction.getSatisfactionOfDensity(this);
        sum+=densitySatisfaction;
        double reciprocitySatisfaction= preference.getReciprocityPreference()* Satisfaction.getSatisfactionOfReciprocity(this);
        sum+=reciprocitySatisfaction;
        double popularitySatisfaction = preference.getPopularityPreference()* Satisfaction.getSatisfactionOfPopularity(this);
        sum+=popularitySatisfaction;
        double activitySatisfaction = preference.getActivityPreference()* Satisfaction.getSatisfactionOfActivity(this);
        sum+=activitySatisfaction;
        double transitivitySatisfaction = preference.getTransitivityPreference()* Satisfaction.getSatisfactionOfTransitivity(this);
        sum+=transitivitySatisfaction;
        double indirectRelationSatisfaction = preference.getIndirectRelationEffectPreference()* Satisfaction.getSatisfactionOfIndirectRelationsEffect(this, preference.getIndirectRelationEffectPreferenceIsWeakConnectionThreshold());
        sum+=indirectRelationSatisfaction;
        double balanceSatisfaction =preference.getBalancePreference()* Satisfaction.getSatisfactionOfBalance(this, preference.getBalancePreferenceB0());
        sum+=balanceSatisfaction;


        if (doLog){
            System.out.println("Density takes ".concat(String.valueOf(densitySatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("reciprocity takes ".concat(String.valueOf(reciprocitySatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("popularity takes ".concat(String.valueOf(popularitySatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("activity takes ".concat(String.valueOf(activitySatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("transitivity takes ".concat(String.valueOf(transitivitySatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("indirectRelation takes ".concat(String.valueOf(indirectRelationSatisfaction/sum)).concat(" of node Satisfaction"));
            System.out.println("balance takes ".concat(String.valueOf(balanceSatisfaction/sum)).concat(" of node Satisfaction"));
        }
//        if (String.valueOf(densitySatisfaction/sum).contains("NaN")){
//            System.out.println("!");
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

    public Node(Net net, int index, Position position, Preference preference, int drawSize, Color color, HashMap<String, Double> args, boolean doLog) {
        this.net = net;
        this.index = index;
        this.position = position;
        this.preference = preference;
        this.drawSize = drawSize;
        this.color = color;
        this.args = args;
        this.doLog = doLog;
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

    public Node copy(Net net){
        Node node = new Node(net, this.index, this.position, this.preference.copy(), this.drawSize, this.color.copy(), new HashMap<>(), this.doLog);
        for (String arg:args.keySet()){
            node.getArgs().put(arg, args.get(arg).doubleValue());
        }
        return node;
    }
}
