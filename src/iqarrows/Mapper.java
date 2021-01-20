package iqarrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Mapper {
	private ArrayList<boolean[]> map = new ArrayList<>();
	
	private final int ROWS = 3;
	private final int COLS = 6;
	private int totalLength;
	
	private int[][] hints;
	
	Mapper(int[][] h) {
		hints = h;
		totalLength = PIECES.length + ROWS*COLS + hints.length;
	}
	
	private final String[] COLORS = {"orange", "yellow", "green", "purple", "red", "blue"};
	
	private final int[][][][] PIECES = {
			{
				 {{0,4}, 
				  {1,2}},
				 {{2,0}, 
			      {3,1}},
				 {{4,3}, 
			      {2,0}}, 
				 {{3,1},
				  {0,4}}
			},
			{
				 {{0,2}, 
				  {3,2}},
				 {{4,0}, 
			      {3,3}},
				 {{4,1}, 
			      {4,0}}, 
				 {{1,1},
				  {0,2}}
			},
			{
				 {{0,2}, 
				  {1,4}},
				 {{2,0}, 
			      {1,3}},
				 {{2,3}, 
			      {4,0}}, 
				 {{1,3},
				  {0,4}}
			},
			{
				 {{3,1}},
				 {{4}, 
			      {2}},
			},
			{
				 {{4}, 
				  {2},
				  {1}},
				 {{2,3,1}},
				 {{3}, 
				  {4},
				  {2}},
				 {{3,1,4}},
		    },
			{
				 {{0,2}, 
				  {0,3},
				  {2,3}},
				 {{3,0,0}, 
				  {4,4,3}},
				 {{1,4}, 
				  {1,0},
				  {4,0}}, 
				 {{1,2,2}, 
				  {0,0,1}},
			},
	};
	
	private class State {
		ArrayList<int[]> occupies; 
		
		State (ArrayList<int[]> indeces) {
			occupies = indeces;
		}
	}
	
	private class Piece {
		ArrayList<State> states = new ArrayList<>(); 
		
		int[][] matrix;
		int color;
		
		Piece (int[][] m, int c) {
			matrix = m;
			color = c;
		}
		
		void calculateStates(){
			int h = matrix.length, w = matrix[0].length;
			
			for (int i=0; i<=ROWS-h; i++) {
				for (int j=0; j<=COLS-w; j++) {
					
					ArrayList<int[]> indeces = new ArrayList<>();
					for (int k=0; k<h; k++) { // loop through each square of the shape
						for (int l=0; l<w; l++) {
							if (matrix[k][l] > 0) {
								int o = matrix[k][l];
								indeces.add(new int[] {l + j, k + i, o});
							}
						}
					}
					State s = new State(indeces);
					states.add(s);
				}
			}
		}
		
	}
	
	private void append(Piece p) {
		for (State s : p.states) {
			boolean[] row = new boolean[totalLength];
			
			row[p.color] = true;
			
			for (int[] square : s.occupies) {
				int x = square[0], y = square[1];
				int index = PIECES.length + y * COLS + x;
				row[index] = true;
				
				for (int i=0; i<hints.length; i++) {
					int[] hint = hints[i];
					if(Arrays.equals(hint, square)) {
						row[totalLength - hints.length + i] = true;
					}
				}
			}
			map.add(row);
		}
		
	}
	
	public String[] generateNames() {
		String[] names = new String[totalLength];
		for (int i=0; i<names.length; i++) {
			if (i < 6) {
				names[i] = COLORS[i];
			} else {
				names[i] = String.valueOf(i);				
			}
		}
		return names;
	}
	
	public boolean[][] generateMap() {
		for (int i=0; i<PIECES.length; i++) {
			int[][][] piece = PIECES[i];
			for (int[][] orientation : piece) {
				Piece p = new Piece(orientation, i); 
				p.calculateStates();
				append(p);
			}
		}
		
		return toArray(map);
	}
	
	private boolean[][] toArray(ArrayList<boolean[]> map) {
		final int rows = map.size();
		final int cols = totalLength;
		
		boolean[][] mapArray = new boolean[rows][cols]; 
		for (int i=0; i<rows; i++) {
			boolean[] row = map.get(i);
			for (int j=0; j<cols; j++) {
				boolean b = row[j];
				mapArray[i][j] = b;
			}
		}
		
		return mapArray;
	}
	
	public void view() {
		for (boolean[] ar : map) {
			System.out.println(Arrays.toString(ar));			
		}
	}
	
}
