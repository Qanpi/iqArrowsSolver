package iqarrows;

import java.awt.*;

public class Board {
	public final static int ROWS = 3;
	public final static int COLS = 6;

	public final static Color EMPTY = new Color(45, 45, 45);
	public final static Color[] COLORS = {
			new Color(250, 166, 25),
			new Color(255, 229, 2),
			new Color(155, 212, 19),
			new Color(125, 71, 177),
			new Color(219, 40, 7),
			new Color(10, 166, 239),
	};

	private Cell[][] state = new Cell[ROWS][COLS];

	public Cell[][] getState() {
		return state;
	}

	Board() {
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				state[i][j] = new Cell(i, j, EMPTY,0);
			}
		}
	}

	Board(int[][][] s) {
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				Color c = COLORS[s[i][j][0]];
				int o = s[i][j][1];
				state[i][j] = new Cell(i, j, c, o);
			}
		}
	}

	Board(BoardPanel.CellPanel[][] s) {
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				BoardPanel.CellPanel cellPanel = s[i][j];
				state[i][j] = new Cell(i, j, cellPanel.getColor(), cellPanel.getOrientation());
			}
		}
	}

	class Cell {
		private int x, y;
		private Color color;
		private int orientation;

		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public Color getColor() {
			return color;
		}
		public int getOrientation() {
			return orientation;
		}

		Cell(int i, int j, Color c, int o) {
			x = j;
			y = i;
			color = c;
			orientation = o;
		}
	}
}
