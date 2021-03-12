package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import iqarrows.DancingLinks.ColumnNode;
import iqarrows.DancingLinks.DancingNode;

public class Solver {
	private static Canvas canvas;
	private static boolean minimizeBranching = true;

	protected void setMinimizeBranching(boolean b) {
		minimizeBranching = b;
	}
	public boolean getMinimizeBranching() {
		return minimizeBranching;
	}

	private static DancingLinks DLX;
	private static ColumnNode DLXROOT;

	public static void main(String[] args) {
		canvas = new Canvas("IQ Arrows Solver", 600, 300);
	}

	public static void solve(Board challenge) {
		long startTime = System.nanoTime();
		Mapper map = new Mapper(challenge);
		DLX = new DancingLinks();
		DLXROOT = DLX.getRoot();
		DLX.fromBinaryMatrix(map.genMatrix(), map.genNames());
		search(0);
		long endTime = System.nanoTime();
		System.out.print("time: ");
		System.out.println((endTime - startTime) / 1000000);

		//if no solution was found, display error
		if(answer.size() == 0) {
			canvas.displayError("No solution was found. Please try again.");
			return;
		}

		Board solution = getSolution();
		canvas.displayBoard(solution);
		PanelListener.setShowingSolution(true);

		answer = new ArrayList<>();
	}

	private static List<DancingNode> temp = new ArrayList<>();
	private static List<List<String>> answer = new ArrayList<>();

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

	private static List<List<String>> handleAnswer() {
		List<List<String>> output = new ArrayList<>();
		for (int i = 0; i < temp.size(); i++) {
			DancingNode n = temp.get(i);

			List<String> row = new ArrayList<>();
			row.add(n.column.name);
			for (DancingNode j = n.right; j != n; j = j.right) {
				row.add(j.column.name);
			}
			Collections.sort(row);
			output.add(row);
		}
		return output;
	}

	private static Board getSolution() {
		int[][][] data = new int[Board.ROWS][Board.COLS][2];
		for (int i=0; i<answer.size(); i++) {
			List<String> row = answer.get(i);

			int color = -1;
			for (int j=0; j<row.size(); j++) {
				String n = row.get(j);
				if (!n.startsWith("h") && Integer.parseInt(n) < 6) {
					color = Integer.parseInt(n);
				}
			}
			
			for (int j=0; j<row.size(); j++) {
				String n = row.get(j);
				if(n.startsWith("h")) {
					int x = Integer.parseInt(String.valueOf(n.charAt(1)));
					int y = Integer.parseInt(String.valueOf(n.charAt(2)));
					int o = Integer.parseInt(String.valueOf(n.charAt(3)));
					data[y][x][1] = o;
				} else if (Integer.parseInt(n) > 5) {
					int t = Integer.parseInt(n);
					int x = t % Board.COLS;
					int y = (t - x) / Board.COLS - 1;
					data[y][x][0] = color;
				}
			}
		}
		return new Board(data);
	}
}