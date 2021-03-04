package iqarrows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PanelListener implements MouseListener {
    public static boolean isShowingSolution() {
        return showingSolution;
    }

    public static void setShowingSolution(boolean showingSolution) {
        PanelListener.showingSolution = showingSolution;
    }

    private static boolean showingSolution = false;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (showingSolution) return;

        Object source = e.getSource();
        if(source instanceof BoardPanel.CellPanel) {
            BoardPanel.CellPanel cellPanel = (BoardPanel.CellPanel) source;

            if (e.getButton() == MouseEvent.BUTTON1) {
                int o = (cellPanel.getOrientation()) % 4 + 1;
                cellPanel.setOrientation(o);
            } else if (e.getButton() == MouseEvent.BUTTON3)
                cellPanel.setOrientation(0);

            cellPanel.getParent().repaint();
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

class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
