package iqarrows;

import java.util.List;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Canvas extends JFrame {
	
	public Canvas(String title, int w, int h) {
		super(title);
        setSize(w, h);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
	}
	
	public void addBoard(int[][] state) {
		Board board = new Board(state);
        add(board);
	}
}

@SuppressWarnings("serial")
class Board extends JPanel {
	final int ROWS = 3;
	final int COLS = 6;
	
	final Color[] COLORS = {Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};
	
	private class Square extends JPanel {
		private int size = 100;
		private Color color; 
		
		Square(Color c) {
			super();
			color = c;
		}
		
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(color);
			g2.fill(new Rectangle2D.Double(0, 0, size, size));
		}
	}
	
	Board(int[][] state) {
		super();
		setLayout(new GridLayout(ROWS, COLS, 0, 0));
		
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				int c = state[i][j];
				Square square = new Square(COLORS[c]);
				add(square);
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
}