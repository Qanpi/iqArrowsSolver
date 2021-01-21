package iqarrows;

import java.util.ArrayList;
import java.util.List;

public class DancingLinks {
	private ColumnNode root;
	
	protected ColumnNode getRoot() {
		return root;
	}
	
	DancingLinks() {
		root = new ColumnNode("root");
	}
	
	//A node class that is used for inheritance
	class DancingNode {
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
	class ColumnNode extends DancingNode {
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
				
				cn.size += 1; //keep count of all the 1s (trues) for minimizing branching				
			}
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
