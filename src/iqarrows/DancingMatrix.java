package iqarrows;

public class DancingMatrix {
	private Node root;
	
	private int rows;
	private int cols;

	DancingMatrix() {
		root = new Node();
	}
	
	//A node class that is used for inheritance
	private class Node {
		Node left, right, up, down;
		
		Node() {
			left = right = up = down = this;
		}
		
		void linkLeft(Node n) {
			this.left = n;
			n.right = this;
		}
		
		void linkRight(Node n) {
			this.right = n;
			n.left = this;
		}
		
		void linkUp(Node n) {
			this.up = n;
			n.down = this;
		}
		
		void linkDown(Node n) {
			this.down = n;
			n.up = this;
		}
	}
	//The individual nodes responsible for the headers of the columns
	private class ColumnNode extends Node {
		int size;
		String name;
		
		ColumnNode(String name) { 
			this.name = name;
		}
	}
	
	//The cells/nodes themselves
	private class Cell extends Node {
		ColumnNode column;
		boolean value;
		
		Cell(ColumnNode cn, boolean value) {
			this.column = cn;
			this.value = value;
		}
	}
	
	public Node getRoot() {
		return root;
	}
	
//	private Node insertLeft(Node n1, Node n2) {
//		n1.left.linkRight(n2);
//		n1.linkLeft(n2);
//		return n2;
//	}
	
	private Node insertRight(Node n1, Node n2) { 
		n1.right.linkLeft(n2);
		n1.linkRight(n2);
		return n2;
	}
	
//	Node insertUp(Node n1, Node n2) {
//		n1.up.linkDown(n2);
//		n1.linkUp(n2);
//		return n2;
//	}

	private Node insertDown(Node n1, Node n2) {
		n1.down.linkUp(n2);
		n1.linkDown(n2);
		return n2;
	}
	
	public void fromBinaryMatrix(boolean[][] matrix, String[] names) {	
		cols = names.length;
		rows = matrix[0].length; //assuming that all the rows in a matrix are of equal size
		
		for (int i=0; i<names.length; i++) {
			ColumnNode cn = new ColumnNode(names[i]);
			insertRight(root.left, cn);
			
			for (boolean[] row : matrix) {
				Cell c = new Cell(cn, row[i]);
				insertDown(cn.up, c);
				if (c.value == true) {cn.size += 1;} //keep count of all the 1s (trues)
				 
				if (cn.left != root) {
					Node leftNeighbour = c.up.left.down;
					insertRight(leftNeighbour, c);
				}
			}
		}
	}
	
	public void coverColumn() {
		
	}
	
	public void view() {
		Object[] names = new String[cols];
		Object[] sizes = new Integer[cols];
		Object[][] values = new Boolean[rows][cols];
		
		if (root.right == root) { //handle the dancing matrix being empty (except for the root element ofc)
			System.out.println("The Dancing matrix only contains a root element.");
			return;
		}
		
		//Store elements in array to later print them out in a table format
		//WARNING: The below loop is a mess. I can't come up with a better way to do it though. 
		Node i = root.right;
		for (int c=0; c<cols; c++) { 
			ColumnNode columnNode = (ColumnNode) i; //type-casting to ColumnNode in order to access .name
			names[c] = columnNode.name;
			sizes[c] = columnNode.size;
			
			Node j = i.down;
			for (int r=0; r<rows; r++) {
				Cell cell = (Cell) j; //type-casting to Cell in order to access .value
				values[r][c] = cell.value;
				j = j.down;
			}
			
			i = i.right;  
		}
		
		//Print out how the matrix looks like for debugging purposes
		System.out.format("%15s%15s%15s%15s\n", names);
		System.out.format("%15s%15s%15s%15s\n\n", sizes);
		for (Object[] row : values) {System.out.format("%15b%15b%15b%15b\n", row);}
	}
	

}
