package iqarrows;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;


public class Canvas extends JFrame {
	public Canvas(String title, int w, int h) {
		super(title);
		setSize(w, h);
		setMinimumSize(new Dimension(500, 324));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void showBoard(int[][][] solution) {
		BoardPanel board = new BoardPanel(solution);
		board.setMaximumSize(new Dimension(1000, 500));

//		JPanel wrapper = new JPanel();
//		wrapper.setLayout(new FlowLayout());
//		wrapper.add(board);

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(board);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {
	//the snapshot is used to determine how to draw borders (where not to draw them)
	private Square[][] snapshot = new Square[Board.ROWS][Board.COLS];
	private class Square extends JPanel {
		private int x, y;
		private int color;
		private int orientation;

		Square(int x, int y, int c, int o) {
			super();
			color = c;
			orientation = o;

//			setBackground(color);
//			setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			int s = getSize().height;
			g2.setPaint(Board.COLORS[color]);
			g2.fill(new Rectangle2D.Double(0, 0, s, s));

//			g2.setPaint(Color.BLACK);
//			g2.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
//
//			if(x > 0 && snapshot[y][x-1] != color) g2.draw(new Line2D.Double(0, 0, 0, s));
////			if(x == Board.COLS-1 || snapshot[y][x+1] != color) g2.draw(new Line2D.Double(s, 0, s, s));
//			if(y > 0 && snapshot[y-1][x] != color) g2.draw(new Line2D.Double(0, 0, s, 0));
////			if(y == Board.ROWS-1 || snapshot[y+1][x] != color) g2.draw(new Line2D.Double(0, s, s, s));
		}
	}

	BoardPanel(int[][][] solution) {
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				int c = solution[i][j][0], o = solution[i][j][1];
				Square square = new Square(j, i, c, o);
				snapshot[i][j] = square;
				add(square);
			}
		}
	}

	@Override
	public Dimension getPreferredSize(){
		Dimension d = getParent().getSize();
		Dimension max = getMaximumSize();

		int pad = 100;
		int h = d.height - pad;
		int w = d.width - pad;

		if (w / h  > 6 / 2 / 3) { //no clue why this needs to be 2/3 instead of just 3
			int newWidth = 6 * h / 3;
			int newHeight = h;
			return new Dimension(newWidth, newHeight);
		} else {
			int newHeight = 3 * w / 6;
			int newWidth = w;
			return new Dimension(newWidth, newHeight);
		}
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		Dimension size = getSize();
		int cs = snapshot[0][0].getSize().height; //stands for cell size

		g2.setPaint(Color.WHITE);
		g2.setStroke(new BasicStroke(cs/10));
		g2.draw(new Rectangle2D.Double(0, 0, size.width, size.height));

		g2.setStroke(new BasicStroke(cs/10/2));
		for(int i=0; i < Board.ROWS; i++){
			for (int j=0; j < Board.COLS; j++) {
				int curr = snapshot[i][j].color;
				int nextX = j + 1 < Board.COLS ? snapshot[i][j+1].color : -1;
				int nextY = i + 1 < Board.ROWS ? snapshot[i+1][j].color : -1;

				AffineTransform originalState = g2.getTransform();
				AffineTransform offset = new AffineTransform();
				offset.translate(j*cs, i*cs);
				g2.transform(offset);

				if(nextX != -1 && curr != nextX) g2.draw(new Line2D.Double(cs, 0, cs, cs));
				if(nextY != -1 && curr != nextY) g2.draw(new Line2D.Double(0, cs, cs, cs));
				g2.setTransform(originalState);
			}
		}
	}
}