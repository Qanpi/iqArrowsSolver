package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;

public class Mapper {
	private ArrayList<boolean[]> map = new ArrayList<>();
	
	private final int ROWS = 3;
	private final int COLS = 6;
	
	private String[] colors = {"orange", "yellow", "green", "purple", "red", "blue"};
	
	int[][][][] shapes = {
			{
				 {{0,1}, 
				  {1,1}},
				 {{1,0}, 
			      {1,1}},
				 {{1,1}, 
			      {1,0}}, 
				 {{1,1},
				  {0,1}}
			},
			{
				 {{0,1}, 
				  {1,1}},
				 {{1,0}, 
			      {1,1}},
				 {{1,1}, 
			      {1,0}}, 
				 {{1,1},
				  {0,1}}
			},
			{
				 {{0,1}, 
				  {1,1}},
				 {{1,0}, 
			      {1,1}},
				 {{1,1}, 
			      {1,0}}, 
				 {{1,1},
				  {0,1}}
			},
			{
				 {{1,1}},
				 {{1}, 
			      {1}},
			},
			{
				 {{1}, 
				  {1},
				  {1}},
				 {{1,1,1}},
		    },
			{
				 {{0,1}, 
				  {0,1},
				  {1,1}},
				 {{1,0,0}, 
				  {1,1,1}},
				 {{1,1}, 
				  {1,0},
				  {1,0}}, 
				 {{1,1,1}, 
				  {0,0,1}},
			},
	};
	
	private class Permutation {
		ArrayList<int[]> occupies; 
		
		Permutation (ArrayList<int[]> indeces) {
			occupies = indeces;
		}
	}
	
	private class Shape {
		ArrayList<Permutation> permutations = new ArrayList<>(); 
		
		int[][] matrix;
		int color;
		
		Shape (int[][] m, int c) {
			matrix = m;
			color = c;
		}
		
		void generatePermutations(){
			int h = matrix.length, w = matrix[0].length;
			
			for (int i=0; i<=ROWS-h; i++) {
				for (int j=0; j<=COLS-w; j++) {
					
					ArrayList<int[]> indeces = new ArrayList<>();
					for (int k=0; k<h; k++) { // loop through each square of the shape
						for (int l=0; l<w; l++) {
							if (matrix[k][l] == 1) {
								indeces.add(new int[] {l + j, k + i});
							}
						}
					}
					Permutation p = new Permutation(indeces);
					permutations.add(p);
				}
			}
		}
		
	}
	
	private void append(Shape s) {
		for (Permutation p : s.permutations) {
			boolean[] row = new boolean[6 + ROWS*COLS];
			
			row[s.color] = true;
			
			for (int[] square : p.occupies) {
				int x = square[0], y = square[1];
				int index = 6 + y * 6 + x;
				row[index] = true;
			}
			map.add(row);
		}
		
	}
	
	public void view() {
		for (boolean[] ar : map) {
			System.out.println(Arrays.toString(ar));			
		}
	}
	
	public boolean[][] createPieces() {
		for (int i=0; i<shapes.length; i++) {
			int[][][] piece = shapes[i];
			for (int[][] orientation : piece) {
				Shape s = new Shape(orientation, i); 
				s.generatePermutations();
				append(s);
			}
		}
		
		return toArray(map);
	}
	
	private boolean[][] toArray(ArrayList<boolean[]> map) {
		final int ROWS = map.size();
		final int COLS = map.get(0).length;
		
		boolean[][] mapArray = new boolean[ROWS][COLS]; 
		for (int i=0; i<ROWS; i++) {
			boolean[] row = map.get(i);
			for (int j=0; j<COLS; j++) {
				boolean b = row[j];
				mapArray[i][j] = b;
			}
		}
		
		return mapArray;
	}
	
}
