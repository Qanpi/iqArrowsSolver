package iqarrows;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import static java.awt.geom.Path2D.WIND_EVEN_ODD;


public class Canvas extends JFrame {
	public Canvas(String title, int w, int h) {
		super(title);
		setSize(w, h);
		setMinimumSize(new Dimension(500, 324));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void emptyBoard(){
		BoardPanel board = new BoardPanel();
		board.setMaximumSize(new Dimension(1000, 500));

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(board);
	}

	public void showBoard(int[][][] solution) {
		BoardPanel board = new BoardPanel(solution);
		board.setMaximumSize(new Dimension(1000, 500));

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(board);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {
	//the snapshot is used to determine how to draw borders (where not to draw them)
	private Square[][] snapshot = new Square[Board.ROWS][Board.COLS];
	class Square extends JPanel {
		private int x, y;
		private Color color;
		private int orientation;

		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}

		public int getOrientation() {
			return orientation;
		}
		public void setOrientation(int orientation) {
			this.orientation = orientation;
		}

		Square(int x, int y, Color c, int o) {
			super();
			color = c;
			orientation = o;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			int s = getSize().height;
			g2.setPaint(color);
			g2.fill(new Rectangle2D.Double(0, 0, s, s));

			//draw an arrow if necessary
			AffineTransform originalState = g2.getTransform();
			AffineTransform at = new AffineTransform();
			at.translate(s/2, s/2);
			at.scale(0.8, 0.8);

			switch(orientation){
				case 0: return;
				case 1: at.rotate(Math.toRadians(-180)); break;
				case 2: at.rotate(Math.toRadians( -90)); break;
				case 3: break; //the original is already rotated this way
				case 4: at.rotate(Math.toRadians(  90)); break;
			}
			g2.setPaint(Color.WHITE);
			g2.setStroke(new BasicStroke(s/10/2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

			GeneralPath arrow = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
			int xPoints[] = {-s/4, s/4, s/4, s/2,   0, -s/2, -s/4};
			int yPoints[] = {-s/2,-s/2,   0,   0, s/2,    0,    0};
			arrow.moveTo(xPoints[0], yPoints[0]);
			for (int i = 1; i < xPoints.length; i++) {
				arrow.lineTo(xPoints[i], yPoints[i]);
			}
			arrow.closePath();

			g2.transform(at);
			g2.draw(arrow);
			g2.setTransform(originalState);
		}
	}

	BoardPanel(){
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		PanelListener listener = new PanelListener();
		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				Square square = new Square(j, i, Color.GRAY, 0);
				square.addMouseListener(listener);
				snapshot[i][j] = square;
				add(square);
			}
		}
	}

	BoardPanel(int[][][] solution) {
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				int c = solution[i][j][0], o = solution[i][j][1];
				Square square = new Square(j, i, Board.COLORS[c], o);
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
		int cellSize = snapshot[0][0].getSize().height; //stands for cell size
		int borderSize = cellSize / 10;

		g2.setPaint(Color.WHITE);
		g2.setStroke(new BasicStroke(borderSize));
		g2.draw(new Rectangle2D.Double(0, 0, size.width, size.height));

		g2.setStroke(new BasicStroke(borderSize/2));
		for(int i=0; i < Board.ROWS; i++){
			for (int j=0; j < Board.COLS; j++) {
				Color curr = snapshot[i][j].color;
				Color nextX = j + 1 < Board.COLS ? snapshot[i][j+1].color : Color.BLACK;
				Color nextY = i + 1 < Board.ROWS ? snapshot[i+1][j].color : Color.BLACK;

				AffineTransform originalState = g2.getTransform();
				AffineTransform offset = new AffineTransform();
				offset.translate(j*cellSize, i*cellSize);
				g2.transform(offset);

				if(nextX != Color.BLACK && curr != nextX) g2.draw(new Line2D.Double(cellSize, 0, cellSize, cellSize));
				if(nextY != Color.BLACK && curr != nextY) g2.draw(new Line2D.Double(0, cellSize, cellSize, cellSize));
				g2.setTransform(originalState);
			}
		}
	}
}