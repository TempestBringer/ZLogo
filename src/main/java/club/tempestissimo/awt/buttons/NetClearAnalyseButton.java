package club.tempestissimo.awt.buttons;

import club.tempestissimo.awt.MainWindow;
import club.tempestissimo.net.analyse.AbstractAnalyser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class NetClearAnalyseButton implements MouseListener {
    private Button button;
    private MainWindow window;
    @Override
    public void mouseClicked(MouseEvent e) {
        List<AbstractAnalyser> tickAnalysers = this.window.getNet().getTickAnalysers();
        for(AbstractAnalyser analyser: tickAnalysers){
            analyser.clearData();
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

    public NetClearAnalyseButton(Button button, MainWindow window) {
        this.button = button;
        this.window = window;
    }

    public Button getButton() {
        return button;
    }
}
