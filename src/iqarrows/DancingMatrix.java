package iqarrows;

import java.util.ArrayList;

public class DancingMatrix {
	private ColumnNode root;

	DancingMatrix() {
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
	
	public ColumnNode getRoot() {
		return root;
	}
	
//	private Node insertLeft(Node n1, Node n2) {
//		n1.left.linkRight(n2);
//		n1.linkLeft(n2);
//		return n2;
//	}

	
	public void fromBinaryMatrix(boolean[][] matrix, String[] names) {	
		final int COLS = names.length;
		final int ROWS = matrix[0].length; //assuming that all the rows in a matrix are of equal size
		ArrayList<ColumnNode> columnNodes = new ArrayList<ColumnNode>();
		
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
				DancingNode c = new DancingNode(cn);
				cn.up.hookDown(c);
				
				if (prev == null) prev = c;
				prev = prev.hookRight(c);
				
				cn.size += 1; //keep count of all the 1s (trues)				
			}
		}
	}
	
	public void view() {	
		for (ColumnNode cn = (ColumnNode) root.right; cn != root; cn = (ColumnNode) cn.right) {
			String ret = cn.name + "-->>";
			for (DancingNode c = cn.down; c != cn; c = c.down) {
				ret += c.column.name + "-->"; 
			}
			System.out.println(ret); 
		}
		System.out.println();
	}
	
	public void test() {
		view();
		ColumnNode cn = ((ColumnNode) root.right).cover();
		view();
		cn.uncover();
		view();
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
 * 
 * */
