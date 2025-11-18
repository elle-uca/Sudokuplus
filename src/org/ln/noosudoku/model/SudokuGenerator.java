package org.ln.noosudoku.model;

/**
 * Abstraction for components capable of producing Sudoku puzzle grids.
 */
public interface SudokuGenerator {

	/**
	 * Provides the generated puzzle as a square matrix of integers.
	 *
	 * @return puzzle matrix where zero represents an empty cell
	 */
	public abstract int[][] getPuzzle();

}
