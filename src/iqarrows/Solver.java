package iqarrows;


public class Solver {
	public static void main(String[] args) {

		int[][] hints = new int[][] {{2, 0, 4}, {4, 0, 4}, {2, 1, 4}, {4, 1, 4}};
		Mapper mapper = new Mapper(hints);
		
		boolean[][] map = mapper.generateMap();
		String[] names = mapper.generateNames();
		
		DancingLinks DLX = new DancingLinks();
		DLX.fromBinaryMatrix(map, names);
		long startTime = System.nanoTime();
		DLX.search(0);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000;
		System.out.println(String.format("The search took: %d microseconds", duration));
	}
} 
