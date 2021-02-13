package iqarrows;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;


public class Canvas extends JFrame {

	public Canvas(String title, int w, int h) {
		super(title);
		setSize(w, h);
		setMinimumSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void showBoard(int[][][] solution) {
		BoardPanel board = new BoardPanel(solution);
		board.setMaximumSize(new Dimension(1000, 500));

		JPanel wrapper = new JPanel();
		wrapper.setLayout(new FlowLayout());
		wrapper.add(board);

		getContentPane().add(wrapper, BorderLayout.CENTER);
	}
}

@SuppressWarnings("serial")
class BoardPanel extends JPanel {
	private class Square extends JPanel {
		private Color color;
		private int orientation;

		Square(Color c, int o) {
			super();
			color = c;
			orientation = o;

			setBackground(color);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			Dimension size = getSize();
			int d = Math.min(size.width, size.height);
			g2.draw(new Rectangle2D.Double(0, 0, d, d));
		}
	}

	BoardPanel(int[][][] solution) {
		super();
		setLayout(new GridLayout(Board.ROWS, Board.COLS));

		for (int i=0; i<Board.ROWS; i++) {
			for (int j=0; j<Board.COLS; j++) {
				int c = solution[i][j][0], o = solution[i][j][1];
				Square square = new Square(Board.COLORS[c], o);
				add(square);
			}
		}
	}

	@Override
	public Dimension getPreferredSize(){
		Dimension d = getParent().getSize();
		Dimension max = getMaximumSize();
		int pad = 100;
		int height = d.height - pad;
		if (d.width / height  > 6 / 2 / 3) { //no clue why this needs to be 2/3 instead of just 3
			int newWidth = 6 * height / 3;
			int newHeight = height;

			if (newWidth > max.width) {
				newWidth = max.width;
				newHeight = max.height;
			}
			return new Dimension(newWidth, newHeight);
		} else {
			int newHeight = 3 * d.width / 6;
			int newWidth = d.width;

			if (newHeight > max.height) {
				newHeight = max.height;
				newWidth = max.width;
			}
			return new Dimension(newWidth, newHeight);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}