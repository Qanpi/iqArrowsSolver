package iqarrows;

public class Solver {
	public static void main(String[] args) {
//		String[] names =      {"1",   "2",  "3",   "4"  };
//		boolean[][] matrix = {{false, true, false, false}, 
//							  {true,  false, true,  true }, 
//							  {false, true, false, false}, 
//							  {true,  true, false, true}};
//		test.fromBinaryMatrix(matrix, names);
//		test.search(0);
		
		Mapper mapper = new Mapper();
		boolean[][] map = mapper.createPieces();
		
		mapper.view();
		
		int cols = map[0].length;
		String[] names = new String[cols];
		for (int i=0; i<cols; i++) {
			names[i] = Integer.toString(i);
		}
		
		DancingLinks DLX = new DancingLinks();
		DLX.fromBinaryMatrix(map, names);
//		DLX.view();
		DLX.search(0);
	}
} 
