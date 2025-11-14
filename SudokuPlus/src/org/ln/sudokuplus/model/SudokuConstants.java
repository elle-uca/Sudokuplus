package org.ln.sudokuplus.model;

import java.awt.Color;
import java.awt.Font;

public class SudokuConstants {

	   /** Size of the board */
	   public static final int GRID_SIZE = 9;
	   /** Size of sub-grid of the board */
	   public static final int SUBGRID_SIZE = 3;
	   /** Color to cell */
	   public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
	   public static final Color FG_GIVEN = new Color(0, 0, 0); // RGB
	   public static final Color FG_NOT_GIVEN = Color.GRAY;
	   public static final Color BG_TO_GUESS  = new Color(255, 255, 255); // RGB
	   public static final Color BG_CORRECT_GUESS = new Color(240, 240, 240);
	   public static final Color FG_CORRECT_GUESS = new Color(0, 0, 255);
	   public static final Color FG_NOTE = new Color(160, 160, 160);
	   public static final Color BG_WRONG_GUESS   = new Color(255, 224, 229);
	   public static final Color FG_WRONG_GUESS   = new Color(255, 0, 0);
	   public static final Color BG_HIGH_CELL = new Color(220, 220, 220); // RGB
	   public static final Color BG_HIGH_NUMBER = new Color(200, 200, 200); // RGB
	   public static final Color BG_FOCUS_LOST = new Color(238, 238, 238);
	   public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);
	   public static final Font FONT_NUMBERS_BOLD = new Font("OCR A Extended", Font.BOLD, 28);

	   // Define named constants for UI sizes
	   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
	   public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
	   public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
	   public static final int TIMER_DELAY_MILLIS = 1000;



		/**
		 * An enumeration of constants to represent the status
		 * of each cell.
		 */
		public enum CellStatus {
			GIVEN,         // clue, no need to guess
			TO_GUESS,      // need to guess - not attempted yet
			CORRECT_GUESS, // need to guess - correct guess
			WRONG_GUESS    // need to guess - wrong guess
		}
		
		/**
		 * An enumeration of constants to represent the mode
		 * of each cell: number or note.
		 */
		public enum CellMode {
			CELLPANEL,       // mode cell
			NOTEPANEL 		// mode note
		}
	   



















}
