package iqarrows;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Canvas extends JFrame {

	public Canvas(String title, int w, int h) {
		super(title);
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void showBoard(int[][][] solution) {
		BoardPanel board = new BoardPanel(solution);
		JPanel boardWrapper = new JPanel();

		boardWrapper.setSize(600, 300);
		boardWrapper.add(board);
		add(boardWrapper, BorderLayout.CENTER);

//		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));
//		contentPane.add(pane);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {

	final Color[] COLORS = {Color.ORANGE, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};

	private class Square extends JPanel {
		private int size = 100;
		private Color color;
		private int orientation;

		Square(Color c, int o) {
			super();
			color = c;
			orientation = o;
		}

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(color);
			g2.fill(new Rectangle2D.Double(0, 0, size, size));

			if (orientation != 0) {
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

	BoardPanel(int[][][] solution) {
		super();
		setSize(600,300);
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				int c = solution[i][j][0], o = solution[i][j][1];
				Square square = new Square(COLORS[c], o);
				add(square);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}