package org.ln.sudokuplus.model;

import java.awt.Font;

/**
 * Collection of immutable configuration values shared across the Sudoku application.
 */
public class SudokuConstants {

	
	   /** Size of the board */
	   public static final int GRID_SIZE = 9;
	   /** Size of sub-grid of the board */
	   public static final int SUBGRID_SIZE = 3;
	   
	   // Define named constants for UI sizes
	   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
	   public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
	   public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
	   public static final int TIMER_DELAY_MILLIS = 1000;
	   
	   public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);
	   public static final Font FONT_NUMBERS_BOLD = new Font("OCR A Extended", Font.BOLD, 28);
	   

	}

