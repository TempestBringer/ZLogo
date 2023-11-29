package club.tempestissimo.awt.PaintArea;

import club.tempestissimo.net.entities.Node;
import club.tempestissimo.net.entities.attributes.Preference;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class AwtMouseListener implements MouseListener {

    private PaintArea paintArea;

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
//        System.out.println(x);
//        System.out.println(y);
        CopyOnWriteArrayList<Node> nodes = paintArea.getNet().getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node curNode = nodes.get(i);
            double x2 = Math.pow(curNode.getPosition().getXPosition()-x, 2);
            double y2 = Math.pow(curNode.getPosition().getYPosition()-y, 2);
            double dist = x2 + y2;
//            System.out.println(dist);
            if (x2 + y2  < Math.pow(curNode.getDrawSize(), 2)){
                Preference nodePref = curNode.getPreference();
                paintArea.getWindow().getNodeIndex().setText(String.valueOf(i));
                paintArea.getWindow().getNodeDensityPreferenceTextField().setText(String.valueOf(nodePref.getDensityPreference()));
                paintArea.getWindow().getNodeReciprocityPreferenceTextField().setText(String.valueOf(nodePref.getReciprocityPreference()));
                paintArea.getWindow().getNodePopularityPreference().setText(String.valueOf(nodePref.getPopularityPreference()));
                paintArea.getWindow().getNodeActivityPreferenceTextField().setText(String.valueOf(nodePref.getActivityPreference()));
                paintArea.getWindow().getNodeTransitivityPreferenceTextField().setText(String.valueOf(nodePref.getTransitivityPreference()));
                paintArea.getWindow().getNodeIndirectRelationEffectPreferenceTextField().setText(String.valueOf(nodePref.getIndirectRelationEffectPreference()));
                paintArea.getWindow().getNodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField().setText(String.valueOf(nodePref.getIndirectRelationEffectPreferenceIsWeakConnectionThreshold()));
                paintArea.getWindow().getNodeBalancePreferenceTextField().setText(String.valueOf(nodePref.getBalancePreference()));
                paintArea.getWindow().getNodeBalancePreferenceB0TextField().setText(String.valueOf(nodePref.getBalancePreferenceB0()));
                break;
            }else {
//                System.out.println(x2 + y2 - Math.pow(curNode.getDrawSize(), 2) < 0);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 鼠标按下事件的处理逻辑
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 鼠标释放事件的处理逻辑
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // 鼠标进入事件的处理逻辑
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // 鼠标离开事件的处理逻辑
    }

    public AwtMouseListener(PaintArea paintArea) {
        this.paintArea = paintArea;
    }
}
