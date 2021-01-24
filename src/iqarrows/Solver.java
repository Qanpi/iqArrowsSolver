package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import iqarrows.DancingLinks.ColumnNode;
import iqarrows.DancingLinks.DancingNode;

public class Solver {
	private static boolean minimizeBranching = true;
	
	private static final int ROWS = 3;
	private static final int COLS = 6;
	
	public boolean getMinimizeBranching() {
		return minimizeBranching;
	}
	
	protected void setMinimizeBranching(boolean b) {
		minimizeBranching = b;
	}
	
	private final static DancingLinks DLX = new DancingLinks();
	private final static ColumnNode DLXROOT = DLX.getRoot();
	
	public static void main(String[] args) {
		int[][] hints = new int[][] {{2, 0, 4}, {4, 0, 4}, {2, 1, 4}, {4, 1, 4}};
		Mapper mapper = new Mapper(hints);
		
		boolean[][] map = mapper.generateMap();
		String[] names = mapper.generateNames();
		
		DLX.fromBinaryMatrix(map, names);
		search(0);
		
		Canvas canvas = new Canvas("test", 600, 300);
		canvas.addBoard(state);
		for (List<Integer> row : answer) {
			final String[] COLORS = {"orange", "yellow", "green", "purple", "red", "blue"};
			for (int i : row) {
				if (i < 6) {
					System.out.print(COLORS[i] + ' ');
				} else if (i >= 24) {
					int h = i % 24 + 1;
					System.out.print("Hint#" + String.valueOf(h) + ' ');
				} else {
					int x = i % 6;
					int y = (i - x) / 6 - 1;
					System.out.print(String.format("[%d, %d]", x, y) + ' ');					
				}
			}
			System.out.println();
		}
	}
	
	private static List<DancingNode> temp = new ArrayList<>();
	private static List<List<Integer>> answer = new ArrayList<>();
	private static int[][] state = new int[ROWS][COLS];
	
	private static void search(int k) {
		if (DLXROOT.right == DLXROOT) {
			generateAnswer();
			generateState();
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
	
	private static void generateAnswer() {
		System.out.println("A solution has been found. Yay!");
		
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
		answer = output; 
	}
	
	private static void generateState() {
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
					state[y][x] = color;				
				}
			}
		}
	}
	
	public static int[][] getState() {
		return state;
	}
//		for (List<Integer> row : output) {
//			final String[] COLORS = {"orange", "yellow", "green", "purple", "red", "blue"};
//			for (int i : row) {
//				if (i < 6) {
//					System.out.print(COLORS[i] + ' ');
//				} else if (i >= 24) {
//					int h = i % 24 + 1;
//					System.out.print("Hint#" + String.valueOf(h) + ' ');
//				} else {
//					int x = i % 6;
//					int y = (i - x) / 6;
//					System.out.print(String.format("[%d, %d]", x, y) + ' ');					
//				}
//			}
//			System.out.println();
//		}
//	}

} 
