package club.tempestissimo.net.utils;

import club.tempestissimo.net.entities.Node;

import java.util.concurrent.CopyOnWriteArrayList;

public class Satisfaction {
    /**
     * 基于密度效应的满足度
     * @param node
     * @return
     */
    public static double getSatisfactionOfDensity(Node node){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);
        double sum=0.0;
        for (int i = 0; i < nodeCount; i++) {
            if (i!=selfIndex){
                sum+=connectionMatrix[selfIndex][i];
            }
        }
        return sum;
    }

    /**
     * 基于互惠效应的满足度
     * @param node
     * @return
     */
    public static double getSatisfactionOfReciprocity(Node node){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);
        double sum=0.0;
        for (int i = 0; i < nodeCount; i++) {
            if (i!=selfIndex){
                sum+=connectionMatrix[selfIndex][i]*connectionMatrix[i][selfIndex];
            }
        }
        return sum;
    }

    /**
     * 基于追捧效应的满足度
     * @param node
     * @return
     */
    public static double getSatisfactionOfPopularity(Node node){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);

        double sum=0.0;
        for (int i = 0; i < nodeCount; i++) {
            double partSum = 0;
            for (int h=0;h<nodeCount;h++){
                if (h!=i&&h!=selfIndex&&selfIndex!=i){
                    partSum+= connectionMatrix[h][i];
                }
            }
            sum += connectionMatrix[selfIndex][i] * partSum;
        }
        return sum;
    }

    /**
     * 基于活跃效应的满足度
     * @param node
     * @return
     */
    public static double getSatisfactionOfActivity(Node node){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);

        double sum=0.0;
        for (int i = 0; i < nodeCount; i++) {
            double partSum = 0;
            for (int h=0;h<nodeCount;h++){
                if (h!=i&&h!=selfIndex&&selfIndex!=i){
                    partSum+= connectionMatrix[i][h];
                }
            }
            sum += connectionMatrix[selfIndex][i] * partSum;
        }
        return sum;
    }

    /**
     * 基于传递效应的满足度
     * @param node
     * @return
     */
    public static double getSatisfactionOfTransitivity(Node node){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);

        double sum=0.0;
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (!(i==j||i==selfIndex||j==selfIndex)){
                    sum += connectionMatrix[selfIndex][i]*connectionMatrix[selfIndex][j]*connectionMatrix[i][j];
                }
            }
        }
        return sum;
    }

    /**
     * 基于间接关系效应的满足度
     * @param node
     * @param isWeakConnectionThreshold 小于该值被认为是弱连接
     * @return
     */
    public static double getSatisfactionOfIndirectRelationsEffect(Node node, double isWeakConnectionThreshold){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);

        double max=0.0;
        for (int i = 0; i < nodeCount; i++) {
            if (connectionMatrix[selfIndex][i]<isWeakConnectionThreshold){
                for (int j = 0; j < nodeCount; j++) {
                    if (connectionMatrix[selfIndex][j]>0&&connectionMatrix[j][i]>0){
                        if (selfIndex!=i&&selfIndex!=j&&i!=j){
                            double strength = connectionMatrix[selfIndex][j]*connectionMatrix[j][i];
                            if (strength>max){
                                max=strength;
                            }
                        }

                    }
                }
            }
        }
        return max;
    }

    /**
     * 基于平衡效应的满足度
     * @param node
     * @param threshold 公式中的b0值
     * @return
     */
    public static double getSatisfactionOfBalance(Node node, double threshold){
        double[][] connectionMatrix = node.getNet().getConnectionMatrix();
        CopyOnWriteArrayList<Node> nodes = node.getNet().getNodes();
        int nodeCount = node.getNet().getNodeCount();
        int selfIndex = nodes.indexOf(node);

        double sum0=0.0;
        double sum1=0.0;
        for (int i = 0; i < nodeCount; i++) {
            sum0+=connectionMatrix[selfIndex][i];
        }
        for (int i = 0; i < nodeCount; i++){
            for (int j = 0; j < nodeCount; j++){
                if (j!=selfIndex&&j!=i&&selfIndex!=i){
                    sum1+=(threshold-Math.abs(connectionMatrix[selfIndex][j]-connectionMatrix[i][j]));
                }
            }
        }
        return sum0*sum1;
    }
}
