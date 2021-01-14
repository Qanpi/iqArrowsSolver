package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;

public class Mapper {
	private ArrayList<boolean[]> map = new ArrayList<>();
	
	private final int ROWS = 3;
	private final int COLS = 6;
	
	enum Color {
		ORANGE,
		YELLOW,
		GREEN,
		PURPLE,
		RED,
		BLUE
	}
	
	private class Permutation {
		ArrayList<int[]> occupies; 
		
		Permutation (ArrayList<int[]> indeces) {
			occupies = indeces;
		}
	}
	
	private class Piece {
		ArrayList<Permutation> permutations = new ArrayList<>(); 
		
		int[][][] shapes;
		Color color;
		
		Piece (int[][][] s, Color c) {
			shapes = s;
			color = c;
		}
		
		void generatePositions(){
			for (int[][] shape : this.shapes) {
				int h = shape.length, w = shape.length;
				
				for (int i=0; i<=ROWS-h; i++) {
					for (int j=0; j<=COLS-w; j++) {
						
						ArrayList<int[]> indeces = new ArrayList<>();
						for (int k=0; k<h; k++) { // loop through each square of the shape
							for (int l=0; l<w; l++) {
								if (shape[k][l] == 1) {
									indeces.add(new int[] {k + j, l + i});
								}
							}
						}
						Permutation p = new Permutation(indeces);
						permutations.add(p);
					}
				}
			}
		}
		
	}
	
	private void append(Piece pc) {
		for (Permutation p : pc.permutations) {
			boolean[] row = new boolean[6 + ROWS*COLS];
			
			int colorIndex = pc.color.ordinal();
			row[colorIndex] = true;
			
			for (int[] square : p.occupies) {
				int x = square[0], y = square[1];
				int index = 5 + y * 6 + x;
				row[index] = true;
			}
			map.add(row);
		}
		
	}
	
	void view() {
		for (boolean[] ar : map) {
			System.out.println(Arrays.toString(ar));			
		}
	}
	
	public void test() {
		int[][][] matrixRepr = new int[][][]{
										 {{0,1}, 
										  {1,1}},
										 {{1,0}, 
									      {1,1}},
										 {{1,1}, 
									      {1,0}}, 
										 {{1,1,
										   0,1}}};
										 
		Piece testPiece = new Piece(matrixRepr, Color.ORANGE);
		testPiece.generatePositions();
		append(testPiece);
		view();
	}
}
