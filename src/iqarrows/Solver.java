package iqarrows;

public class Solver {
	public static void main(String[] args) {
		DancingMatrix test = new DancingMatrix();
		boolean[][] matrix = {{false, true, false, false}, 
							  {true,  true, true,  true}, 
							  {false, true, false, false}, 
							  {true,  true, false, true}};
		String[] names = {"1", "2", "3", "4"};
		test.fromBinaryMatrix(matrix, names);
		test.view();
		test.test();
		test.view();
	}
} 
