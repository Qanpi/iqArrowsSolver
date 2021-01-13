package iqarrows;

public class Solver {
	public static void main(String[] args) {
		DancingMatrix test = new DancingMatrix();
		String[] names =      {"1",   "2",  "3",   "4"  };
		boolean[][] matrix = {{false, true, false, false}, 
							  {true,  true, true,  true }, 
							  {false, true, false, false}, 
							  {true,  true, false, true}};
		test.fromBinaryMatrix(matrix, names);
		test.test();
	}
} 
