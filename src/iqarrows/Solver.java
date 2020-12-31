package iqarrows;

public class Solver {
	public static void main(String[] args) {
		DancingMatrix test = new DancingMatrix();
		boolean[][] matrix = {{false,true,false, false}, {true,true,true, true}, {false,true,false, false}, {true, true, false, true}};
		String[] names = {"one", "two", "three", "four"};
		test.fromBinaryMatrix(matrix, names);
		test.view();
	}
} 
