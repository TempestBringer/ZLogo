package club.tempestissimo.awt.buttons;

import club.tempestissimo.awt.MainWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NetNoTickButton implements MouseListener {
    private Button button;
    private MainWindow window;
    @Override
    public void mouseClicked(MouseEvent e) {
        this.window.getNet().setDoTick(false);
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

    public NetNoTickButton(Button button, MainWindow window) {
        this.button = button;
        this.window = window;
    }

    public Button getButton() {
        return button;
    }
}
