package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import iqarrows.DancingLinks.ColumnNode;
import iqarrows.DancingLinks.DancingNode;

public class Solver {
	private static boolean minimizeBranching = true;

	protected void setMinimizeBranching(boolean b) {
		minimizeBranching = b;
	}
	public boolean getMinimizeBranching() {
		return minimizeBranching;
	}

	private final static DancingLinks DLX = new DancingLinks();
	private final static ColumnNode DLXROOT = DLX.getRoot();

	private static int[][] h = new int[][] {{2, 0, 4}, {4, 0, 4}, {2, 1, 4}, {4, 1, 4}}; //to be re-factored

	public static void main(String[] args) {
		Mapper mapper = new Mapper(h);

		DLX.fromBinaryMatrix(mapper.generateMatrix(), mapper.generateNames());
		search(0);

		int[][][] solution = getBoard();

		Canvas canvas = new Canvas("IQ Arrows Solver", 600, 300);
		canvas.emptyBoard();
	}

	private static List<DancingNode> temp = new ArrayList<>();
	private static List<List<Integer>> answer = new ArrayList<>();

	private static void search(int k) {
		if (DLXROOT.right == DLXROOT) {
			answer = handleAnswer();
			return;
		}

		ColumnNode cn;
		if (minimizeBranching) cn = pickColumn();
		else cn = (ColumnNode) DLXROOT.right;

		cn.cover();

		for (DancingNode i = cn.down; i != cn; i = i.down) {
			temp.add(i);
			for (DancingNode j = i.right; j != i; j = j.right) {
				j.column.cover();
			}

			search(k + 1);
			// Clean up if the path is a dead end
			temp.remove(k);
			cn = i.column;
			for (DancingNode j = i.left; j != i; j = j.left) {
				j.column.uncover();
			}
		}

		cn.uncover();
		return;
	}

	private static ColumnNode pickColumn() {
		int min = Integer.MAX_VALUE;
		ColumnNode cn = null;
		for (ColumnNode i = (ColumnNode) DLXROOT.right; i != DLXROOT; i = (ColumnNode) i.right) {
			if (i.size < min) {
				min = i.size;
				cn = i;
			}
		}
		return cn;
	}

	private static List<List<Integer>> handleAnswer() {
		List<List<Integer>> output = new ArrayList<>();

		for (int i = 0; i < temp.size(); i++) {
			DancingNode n = temp.get(i);

			List<Integer> row = new ArrayList<>();
			row.add(Integer.parseInt(n.column.name));
			for (DancingNode j = n.right; j != n; j = j.right) {
				row.add(Integer.parseInt(j.column.name));
			}
			Collections.sort(row);
			output.add(row);
		}
		return output;
	}

	private static int[][][] getBoard() {
		int[][][] data = new int[Board.ROWS][Board.COLS][2];
		for (int i=0; i<answer.size(); i++) {
			List<Integer> row = answer.get(i);
			int color = -1;
			for (int j=0; j<row.size(); j++) {
				int n = row.get(j);
				if (n < 6) {
					color = n;
				} else if (n < 24) {
					int x = n % 6;
					int y = (n - x) / 6 - 1;
					data[y][x][0] = color;
				} else {
					int[] hint = h[n-24];
					int x = hint[0], y = hint[1], o = hint[2];
					data[y][x][1] = o;
					System.out.print(Arrays.toString(data[y][x]));
				}
			}
		}
		return data;
	}
}