package org.ln.sudokuplus.model;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

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
	   
	   /** Color to cell */
	   
//		Old	   
//	   public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
//	   public static final Color BG_TO_GUESS  = new Color(255, 255, 255); // RGB
//	   public static final Color BG_CORRECT_GUESS = new Color(240, 240, 240);	   
//	   public static final Color BG_WRONG_GUESS   = new Color(255, 224, 229);
//	   public static final Color BG_HIGH_CELL = new Color(220, 220, 220); // RGB
//	   public static final Color BG_HIGH_NUMBER = new Color(200, 200, 200); // RGB
//	   public static final Color BG_FOCUS_LOST = new Color(238, 238, 238);	   
//	   
//	   public static final Color FG_GIVEN = new Color(0, 0, 0); // RGB
//	   public static final Color FG_NOT_GIVEN = Color.GRAY;
//	   public static final Color FG_CORRECT_GUESS = new Color(0, 0, 255);
//	   public static final Color FG_NOTE = new Color(160, 160, 160);
//	   public static final Color FG_WRONG_GUESS   = new Color(255, 0, 0);





//		3) PASTEL (molto soft, estetica moderna “notion style”)
//	   public static final Color BG_GIVEN         = new Color(255, 255, 255);
//	   public static final Color BG_TO_GUESS      = new Color(255, 255, 255);
//	   public static final Color BG_CORRECT_GUESS = new Color(210, 245, 210);   // pastel green
//	   public static final Color BG_WRONG_GUESS   = new Color(255, 215, 230);   // pastel pink
//	   public static final Color BG_HIGH_CELL     = new Color(230, 240, 255);   // sky blue
//	   public static final Color BG_HIGH_NUMBER   = new Color(255, 245, 210);   // pastel yellow
//	   public static final Color BG_FOCUS_LOST    = new Color(245, 245, 245);
//
//	   public static final Color FG_GIVEN         = new Color(40, 40, 40);
//	   public static final Color FG_NOT_GIVEN     = new Color(150, 150, 150);
//	   public static final Color FG_CORRECT_GUESS = new Color(50, 120, 50);
//	   public static final Color FG_NOTE          = new Color(160, 160, 160);
//	   public static final Color FG_WRONG_GUESS   = new Color(220, 60, 90);

	   //1) CLASSIC SOFT
//	   public static final Color BG_GIVEN         = new Color(245, 245, 245); // light gray
//	   public static final Color BG_TO_GUESS      = new Color(255, 255, 255); // white
//	   public static final Color BG_CORRECT_GUESS = new Color(230, 250, 230); // soft green
//	   public static final Color BG_WRONG_GUESS   = new Color(255, 220, 220); // soft red
//	   public static final Color BG_HIGH_CELL     = new Color(225, 240, 255); // light blue
//	   public static final Color BG_HIGH_NUMBER   = new Color(210, 230, 250); // slightly stronger blue
//	   public static final Color BG_FOCUS_LOST    = new Color(238, 238, 238);
//	   public static final Color FG_GIVEN         = new Color(0, 0, 0);
//	   public static final Color FG_NOT_GIVEN     = new Color(100, 100, 100);
//	   public static final Color FG_CORRECT_GUESS = new Color(30, 100, 30);  // greenish
//	   public static final Color FG_NOTE          = new Color(140, 140, 140);
//	   public static final Color FG_WRONG_GUESS   = new Color(180, 0, 0);
	  
	   // 2) DARK MODE (contrasto elegante, non accecante)
//	   public static final Color BG_GIVEN         = new Color(55, 55, 55);
//	   public static final Color BG_TO_GUESS      = new Color(40, 40, 40);
//	   public static final Color BG_CORRECT_GUESS = new Color(70, 100, 70);
//	   public static final Color BG_WRONG_GUESS   = new Color(130, 50, 50);
//	   public static final Color BG_HIGH_CELL     = new Color(75, 75, 75);
//	   public static final Color BG_HIGH_NUMBER   = new Color(90, 90, 90);
//	   public static final Color BG_FOCUS_LOST    = new Color(60, 60, 60);
//	   public static final Color FG_GIVEN         = new Color(230, 230, 230);
//	   public static final Color FG_NOT_GIVEN     = new Color(180, 180, 180);
//	   public static final Color FG_CORRECT_GUESS = new Color(120, 200, 120);
//	   public static final Color FG_NOTE          = new Color(150, 150, 150);
//	   public static final Color FG_WRONG_GUESS   = new Color(255, 100, 100);
   
	   ///4) HIGH CONTRAST
//	   public static final Color BG_GIVEN         = new Color(255, 255, 255);
//	   public static final Color BG_TO_GUESS      = new Color(240, 240, 240);
//	   public static final Color BG_CORRECT_GUESS = new Color(180, 255, 180);
//	   public static final Color BG_WRONG_GUESS   = new Color(255, 140, 140);
//	   public static final Color BG_HIGH_CELL     = new Color(255, 255, 100);
//	   public static final Color BG_HIGH_NUMBER   = new Color(255, 200, 50);
//	   public static final Color BG_FOCUS_LOST    = new Color(230, 230, 230);
//	   
//	   public static final Color FG_GIVEN         = new Color(0, 0, 0);
//	   public static final Color FG_NOT_GIVEN     = new Color(80, 80, 80);
//	   public static final Color FG_CORRECT_GUESS = new Color(0, 120, 0);
//	   public static final Color FG_NOTE          = new Color(100, 100, 100);
//	   public static final Color FG_WRONG_GUESS   = new Color(200, 0, 0);

	    // COLOR VARIABLES (no more final)
	    public static Color BG_GIVEN;
	    public static Color BG_TO_GUESS;
	    public static Color BG_CORRECT_GUESS;
	    public static Color BG_WRONG_GUESS;
	    public static Color BG_HIGH_CELL;
	    public static Color BG_HIGH_NUMBER;
	    public static Color BG_FOCUS_LOST;

	    public static Color FG_GIVEN;
	    public static Color FG_NOT_GIVEN;
	    public static Color FG_CORRECT_GUESS;
	    public static Color FG_NOTE;
	    public static Color FG_WRONG_GUESS;
	   
	   
	// ============================================================
	    //                     THEME SUPPORT
	    // ============================================================

	    public enum Theme {
	        CLASSIC,
	        DARK,
	        PASTEL
	    }

	   
	    private static Theme currentTheme = Theme.PASTEL;

	    public static Theme getTheme() {
	        return currentTheme;
	    }

	    public static void setTheme(Theme theme) {
	        currentTheme = Objects.requireNonNull(theme, "theme must not be null");
	        applyThemeColors(theme);
	    }

	    static {
	        applyThemeColors(currentTheme);
	    }


	    private static void applyThemeColors(Theme theme) {

	        switch (theme) {

	            case CLASSIC -> {
	                BG_GIVEN         = new Color(240, 240, 240);
	                BG_TO_GUESS      = new Color(255, 255, 255);
	                BG_CORRECT_GUESS = new Color(240, 240, 240);
	                BG_WRONG_GUESS   = new Color(255, 224, 229);
	                BG_HIGH_CELL     = new Color(220, 220, 220);
	                BG_HIGH_NUMBER   = new Color(200, 200, 200);
	                BG_FOCUS_LOST    = new Color(238, 238, 238);

	                FG_GIVEN         = new Color(0, 0, 0);
	                FG_NOT_GIVEN     = Color.GRAY;
	                FG_CORRECT_GUESS = new Color(0, 0, 255);
	                FG_NOTE          = new Color(160, 160, 160);
	                FG_WRONG_GUESS   = new Color(255, 0, 0);
	            }

	            case DARK -> {
	                BG_GIVEN         = new Color(55, 55, 55);
	                BG_TO_GUESS      = new Color(40, 40, 40);
	                BG_CORRECT_GUESS = new Color(70, 100, 70);
	                BG_WRONG_GUESS   = new Color(130, 50, 50);
	                BG_HIGH_CELL     = new Color(75, 75, 75);
	                BG_HIGH_NUMBER   = new Color(90, 90, 90);
	                BG_FOCUS_LOST    = new Color(60, 60, 60);

	                FG_GIVEN         = new Color(230, 230, 230);
	                FG_NOT_GIVEN     = new Color(180, 180, 180);
	                FG_CORRECT_GUESS = new Color(120, 200, 120);
	                FG_NOTE          = new Color(150, 150, 150);
	                FG_WRONG_GUESS   = new Color(255, 100, 100);
	            }

	            case PASTEL -> {
	                BG_GIVEN         = new Color(255, 255, 255);
	                BG_TO_GUESS      = new Color(255, 255, 255);
	                BG_CORRECT_GUESS = new Color(210, 245, 210);
	                BG_WRONG_GUESS   = new Color(255, 215, 230);
	                BG_HIGH_CELL     = new Color(230, 240, 255);
	                BG_HIGH_NUMBER   = new Color(255, 245, 210);
	                BG_FOCUS_LOST    = new Color(245, 245, 245);

	                FG_GIVEN         = new Color(40, 40, 40);
	                FG_NOT_GIVEN     = new Color(150, 150, 150);
	                FG_CORRECT_GUESS = new Color(50, 120, 50);
	                FG_NOTE          = new Color(160, 160, 160);
	                FG_WRONG_GUESS   = new Color(220, 60, 90);
	            }
	        }
	    }
	}

