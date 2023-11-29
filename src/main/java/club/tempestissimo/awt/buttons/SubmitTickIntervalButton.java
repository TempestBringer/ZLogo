package club.tempestissimo.awt.buttons;

import club.tempestissimo.awt.MainWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SubmitTickIntervalButton implements MouseListener {
    private Button button;
    private MainWindow window;
    @Override
    public void mouseClicked(MouseEvent e) {
        String text = this.window.getTickInterval().getText();
        try{
            Integer newTickInterval = Integer.parseInt(text);
            window.getNet().setDoTickInterval(newTickInterval);
        }catch (Exception exception){
            int doTickInterval = window.getNet().getDoTickInterval();
            this.window.getTickInterval().setText(String.valueOf(doTickInterval));
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

    public SubmitTickIntervalButton(Button button, MainWindow window) {
        this.button = button;
        this.window = window;
    }

    public Button getButton() {
        return button;
    }
}
