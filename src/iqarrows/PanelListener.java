package iqarrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PanelListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if(source instanceof BoardPanel.Square) {
            BoardPanel.Square square = (BoardPanel.Square) source;

            if (e.getButton() == MouseEvent.BUTTON1) {
                int o = (square.getOrientation()) % 4 + 1;
                square.setOrientation(o);
            } else if (e.getButton() == MouseEvent.BUTTON3)
                square.setOrientation(0);
            square.getParent().repaint();
        }
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
}
