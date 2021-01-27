package iqarrows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Canvas extends JFrame {
	
	public Canvas(String title, int w, int h) {
		super(title);
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public void showBoard(Board solution) {
		BoardPanel pane = new BoardPanel(solution);
        add(pane);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {
	
	final Color[] COLORS = {Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};
	
	private class Square extends JPanel {
		private int size = 100;
		private Color color; 
		private int direction; 
		
		Square(Color c) {
			super();
			color = c;
		}
		
		protected void setDirection(int d) {
			direction = d;
		}
		
		protected int getDirection() {
			return direction;
		}
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(color);
			g2.fill(new Rectangle2D.Double(0, 0, size, size));
			
			if (direction != 0) {
				g2.setPaint(Color.BLACK);
				g2.setStroke(new BasicStroke(5.0f));
				
				GeneralPath arrow = new GeneralPath();
				arrow.moveTo(size, size/2);
				arrow.lineTo(0, size/2);
				arrow.lineTo(size/3, size/4);
				arrow.moveTo(0, size/2);
				arrow.lineTo(size/3, size - size/4);
				g2.draw(arrow);
			}
		}
	}
	
	BoardPanel(Board solution) {
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));
		
		Square[][] temp = new Square[Board.ROWS][Board.COLS];
		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				int c = solution.getCells()[i][j];
				Square square = new Square(COLORS[c]);
				add(square);
				
				temp[i][j] = square;	
			}
		}
		
		for(int[] hint : solution.getArrows()) {
			int x = hint[0], y = hint[1];
			temp[y][x].setDirection(hint[2]);
			System.out.println(temp[y][x].getDirection());
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
}