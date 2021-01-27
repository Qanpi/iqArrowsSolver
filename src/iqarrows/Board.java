package iqarrows;

import java.util.Arrays;
import java.util.HashMap;

public class Board {
	
	public final static int ROWS = 3;
	public final static int COLS = 6;
	
	private int[][] cells;
	private int[][] arrows;	
	
	protected void setArrows(int[][]  h) {
		arrows = h;
	}
	
	public int[][]  getArrows() {
		return arrows;
	}
	
	Board() {
		cells = new int[ROWS][COLS];
	}
	
	Board(int[][] data) {
		try {
			setCells(data);
		} catch (IncorrectInputDimensions e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	class IncorrectInputDimensions extends Exception {
	    private String code;

	    public IncorrectInputDimensions(String code, String message) {
	        super(message);
	        this.setCode(code);
	    }

	    public IncorrectInputDimensions(String code, String message, Throwable cause) {
	        super(message, cause);
	        this.setCode(code);
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }
	}

	public int[][] getCells() {
		return cells;
	}

	private void setCells(int[][] data) throws IncorrectInputDimensions {
		if (data.length != ROWS) {
			throw new IncorrectInputDimensions("BOARD_ROW_EXCEPTION", 
					"The number of rows of the input did not match the predefined one");
		}
		for (int[] row : data) {
			if (row.length != COLS) {
				throw new IncorrectInputDimensions("BOARD_COLUMN_EXCEPTION", 
						"The number of columns of the input did not match the predefined one");
			}
		}
		
		this.cells = data;
	}
	
	public void view() {
		for (int[] row : cells) {
			System.out.println(Arrays.toString(row));
		}
	}
}

