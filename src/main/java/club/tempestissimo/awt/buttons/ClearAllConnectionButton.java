package club.tempestissimo.awt.buttons;

import club.tempestissimo.awt.MainWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClearAllConnectionButton implements MouseListener {
    private Button button;
    private MainWindow window;
    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i=0;i<window.getNet().getNodeCount();i++)
            for(int j=0;j<window.getNet().getNodeCount();j++)
                window.getNet().getConnectionMatrix()[i][j] = 0;
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

    public ClearAllConnectionButton(Button button, MainWindow window) {
        this.button = button;
        this.window = window;
    }

    public Button getButton() {
        return button;
    }
}
