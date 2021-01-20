package iqarrows;


public class Solver {
	public static void main(String[] args) {

		int[][] hints = new int[][] {{2, 0, 4}, {4, 0, 4}, {2, 1, 4}, {4, 1, 4}};
		Mapper mapper = new Mapper(hints);
		
		boolean[][] map = mapper.generateMap();
		String[] names = mapper.generateNames();
		
		DancingLinks DLX = new DancingLinks();
		DLX.fromBinaryMatrix(map, names);
//		DLX.view();
		DLX.search(0);
	}
} 
