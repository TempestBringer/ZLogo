package club.tempestissimo.examples.homework1.initializer;

import club.tempestissimo.net.entities.Net;
import club.tempestissimo.net.entities.attributes.Preference;
import club.tempestissimo.net.initialize.AbstractInitializer;

import java.util.List;

public class SatisfactionPreferInitialize implements AbstractInitializer {
    public Preference preference;
    /**
     * 使用参数初始化网络中节点对各种满意度的偏好权重
     * @param net
     */
    @Override
    public void initiate(Net net) {
        for (int i = 0; i < net.getNodeCount(); i++) {
            net.getNodes().get(i).setPreference(preference);
        }
    }

    public SatisfactionPreferInitialize(Preference preference) {
        this.preference = preference;
    }
}
