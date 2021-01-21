package iqarrows;

import java.util.ArrayList;
import java.util.List;

public class DancingLinks {
	private boolean minimizeBranching = true;
	
	private ColumnNode root;

	DancingLinks() {
		root = new ColumnNode("root");
	}
	
	//A node class that is used for inheritance
	private class DancingNode {
		DancingNode left, right, up, down;
		ColumnNode column;
		
		DancingNode() {
			left = right = up = down = this;
		}
		
		DancingNode(ColumnNode cn) {
			this();
			column = cn;
		}
			
		void link(DancingNode n, String dir) {
			switch (dir) {
				case "left":
					this.left = n;
					n.right = this;
					break;
				case "right":
					this.right = n;
					n.left = this;
					break;
				case "up":
					this.up = n;
					n.down = this;
					break;
				case "down":
					this.down = n;
					n.up = this;
					break;
			}
		}
		
		DancingNode hookRight(DancingNode n) { 
			this.right.link(n, "left");
			this.link(n, "right");
			return n;
		}

		DancingNode hookDown(DancingNode n) {
			this.down.link(n, "up");
			this.link(n, "down");
			return n; 
		}
		
		void unlinkLR() {
			this.right.link(this.left, "left");
		}
		
		void unlinkUD() {
			this.down.link(this.up, "up");
		}
		
		void relinkLR() {
			this.right.link(this, "left");
			this.left.link(this, "right");
		}
		
		void relinkUD() {
			this.down.link(this, "up");
			this.up.link(this, "down");
		}
	}
	//The individual nodes responsible for the headers of the columns
	private class ColumnNode extends DancingNode {
		int size;
		String name;
				
		ColumnNode(String name) { 
			super();
			this.name = name;
		}
		
		ColumnNode cover() {
			this.unlinkLR();
			for (DancingNode i = this.down; i != this; i = i.down) {
				for (DancingNode j = i.right; j != i; j = j.right) {
					j.unlinkUD();
					j.column.size--;
				}
			}
			
			return this;
		}
		
		void uncover() {
			for (DancingNode i = this.up; i != this; i = i.up) {
				for (DancingNode j = i.left; j != i; j = j.left) {
					j.relinkUD();
					j.column.size++;
				}
			}
			this.relinkLR();
		}
	}
	
	public void fromBinaryMatrix(boolean[][] matrix, String[] names) {	
		
		final int ROWS = matrix.length; //assuming that all the rows in a matrix are of equal size
		final int COLS = names.length;
		List<ColumnNode> columnNodes = new ArrayList<ColumnNode>();
		
		
		for (int i=0; i<COLS; i++) {
			ColumnNode cn = new ColumnNode(names[i]);
			root.left.hookRight(cn);
			columnNodes.add(cn);
		}
		
		for (int i=0; i<ROWS; i++) {
			DancingNode prev = null; 
			for (int j=0; j<COLS; j++) {
				if (matrix[i][j] == false) continue;
				
				ColumnNode cn = columnNodes.get(j);
				DancingNode n = new DancingNode(cn);
				cn.up.hookDown(n);
				
				if (prev == null) prev = n;
				prev = prev.hookRight(n);
				
				cn.size += 1; //keep count of all the 1s (trues)				
			}
		}
	}
	
	private List<DancingNode> answer = new ArrayList<DancingNode>(); 
	
	public void search(int k) {
		if (root.right == root) {
			handleAnswer();			
			return;
		}
		
		ColumnNode cn;
		if (minimizeBranching) cn = pickColumn();
		else cn = (ColumnNode) root.right;
		
		cn.cover();
		
		for (DancingNode i = cn.down; i != cn; i = i.down) {
			answer.add(i);
			for (DancingNode j = i.right; j != i; j = j.right) {
				j.column.cover();
			}
			
			search(k + 1);
			// Clean up if the path is a dead end
			answer.remove(k);
			cn = i.column;
			for (DancingNode j = i.left; j != i; j = j.left) {
				j.column.uncover();
			}
		}
		
		cn.uncover();
		return;
	}
	
	private ColumnNode pickColumn() {
		int min = Integer.MAX_VALUE; 
		ColumnNode cn = null;
		for (ColumnNode i = (ColumnNode) root.right; i != root; i = (ColumnNode) i.right) {
			if (i.size < min) {
				min = i.size;
				cn = i;
			}
		}
		return cn;
	}
	
	private void handleAnswer() {
		System.out.println("A solution has been found. Yay!");
		
		for (DancingNode n : answer) {
			ArrayList<Integer> row = new ArrayList<>();
			
			row.add(Integer.parseInt(n.column.name));
			for (DancingNode j = n.right; j != n; j = j.right) {
				row.add(Integer.parseInt(j.column.name));

			}
			
			final String[] COLORS = {"orange", "yellow", "green", "purple", "red", "blue"};
			for (int i : row) {
				if (i < 6) {
					System.out.print(COLORS[i] + ' ');
				} else if (i >= 24) {
					int h = i % 24 + 1;
					System.out.print("Hint#" + String.valueOf(h) + ' ');
				} else {
					int x = i % 6;
					int y = (i - x) / 6;
					System.out.print(String.format("[%d, %d]", x, y) + ' ');					
				}
			}
			System.out.println();
		}

	}
	
	public void view() {	
		for (ColumnNode cn = (ColumnNode) root.right; cn != root; cn = (ColumnNode) cn.right) {
			String ret = cn.name + "-->>";
			for (DancingNode n = cn.down; n != cn; n = n.down) {
				ret += n.column.name + "-->"; 
			}
			System.out.println(ret); 
		}
		System.out.println();
	}
	
}


/*
 * TODO:
 * !- Lots of cleanup
 * 	!- Method returns
 * 	!- View function
 * 	!- Loops
 *  !- Cols and rows variables
 *  !- Scopes and OOP
 * - Fix the weird answer doubling issue
 * - Fix the fucking fromBinaryMatrix bug
 * 
 * */
