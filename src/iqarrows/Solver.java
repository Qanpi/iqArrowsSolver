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
		int[][] hints = new int[][] {{2, 0, 4}, {4, 0, 4}, {2, 1, 4}, {4, 1, 4}};
		Mapper mapper = new Mapper(hints);
		boolean[][] map = mapper.generateMap();
		
//		mapper.view();
		
		int cols = map[0].length;
		String[] names = new String[cols];
		for (int i=0; i<cols; i++) {
			if (i < 6) {
				names[i] = mapper.COLORS[i];
			} else {
				names[i] = String.valueOf(i);				
			}
		}
		
		DancingLinks DLX = new DancingLinks();
		DLX.fromBinaryMatrix(map, names);
//		DLX.view();
		DLX.search(0);
	}
} 
