package club.tempestissimo.awt.buttons;

import club.tempestissimo.awt.MainWindow;
import club.tempestissimo.net.entities.attributes.Preference;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SubmitNodePreferenceButton implements MouseListener {
    private Button button;
    private MainWindow window;
    @Override
    public void mouseClicked(MouseEvent e) {
        String index = this.window.getNodeIndex().getText();
        String text1 = this.window.getNodeDensityPreferenceTextField().getText();
        String text2 = this.window.getNodeReciprocityPreferenceTextField().getText();
        String text3 = this.window.getNodePopularityPreference().getText();
        String text4 = this.window.getNodeActivityPreferenceTextField().getText();
        String text5 = this.window.getNodeTransitivityPreferenceTextField().getText();
        String text6 = this.window.getNodeIndirectRelationEffectPreferenceTextField().getText();
        String text7 = this.window.getNodeIndirectRelationEffectPreferenceIsWeakConnectionThresholdTextField().getText();
        String text8 = this.window.getNodeBalancePreferenceTextField().getText();
        String text9 = this.window.getNodeBalancePreferenceB0TextField().getText();
        try{
            int newIndex = Integer.parseInt(index);
            double new1 = Double.parseDouble(text1);
            double new2 = Double.parseDouble(text2);
            double new3 = Double.parseDouble(text3);
            double new4 = Double.parseDouble(text4);
            double new5 = Double.parseDouble(text5);
            double new6 = Double.parseDouble(text6);
            double new7 = Double.parseDouble(text7);
            double new8 = Double.parseDouble(text8);
            double new9 = Double.parseDouble(text9);
            Preference newPref = new Preference(new1,new2,new3,new4,new5,new6,new7,new8,new9);
            window.getNet().getNodes().get(newIndex).setPreference(newPref);
        }catch (Exception exception){
            exception.printStackTrace();
            // DO Nothing
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public SubmitNodePreferenceButton(Button button, MainWindow window) {
        this.button = button;
        this.window = window;
    }

    public Button getButton() {
        return button;
    }
}
