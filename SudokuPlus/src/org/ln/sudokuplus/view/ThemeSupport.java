package org.ln.sudokuplus.view;

import java.awt.Color;
import java.util.Objects;

import org.ln.sudokuplus.enums.Theme;

public class ThemeSupport {
	
    // COLOR VARIABLES 
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

    private static Theme current = Theme.PASTEL;

    public static Theme getTheme() {
        return current;
    }

    public static void setTheme(Theme theme) {
        current = Objects.requireNonNull(theme, "theme cannot be null");
        applyThemeColors(theme);
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
            case SOLARIZED_LIGHT -> {
                BG_GIVEN         = new Color(253, 246, 227);  // base3
                BG_TO_GUESS      = new Color(255, 255, 255);
                BG_CORRECT_GUESS = new Color(238, 232, 213);  // base2
                BG_WRONG_GUESS   = new Color(255, 205, 210);  // soft red
                BG_HIGH_CELL     = new Color(238, 232, 213);
                BG_HIGH_NUMBER   = new Color(231, 221, 199);
                BG_FOCUS_LOST    = new Color(247, 243, 223);

                FG_GIVEN         = new Color(101, 123, 131);  // base00
                FG_NOT_GIVEN     = new Color(131, 148, 150);  // base0
                FG_CORRECT_GUESS = new Color(38, 139, 210);   // blue
                FG_NOTE          = new Color(147, 161, 161);  // base1
                FG_WRONG_GUESS   = new Color(220, 50, 47);    // red
            }
            case SOLARIZED_DARK -> {
                BG_GIVEN         = new Color(0, 43, 54);      // base03
                BG_TO_GUESS      = new Color(7, 54, 66);      // base02
                BG_CORRECT_GUESS = new Color(88, 110, 117);   // base01
                BG_WRONG_GUESS   = new Color(220, 50, 47);    // red
                BG_HIGH_CELL     = new Color(0, 43, 54);
                BG_HIGH_NUMBER   = new Color(38, 50, 56);
                BG_FOCUS_LOST    = new Color(7, 54, 66);

                FG_GIVEN         = new Color(253, 246, 227);  // base3
                FG_NOT_GIVEN     = new Color(147, 161, 161);  // base1
                FG_CORRECT_GUESS = new Color(133, 153, 0);    // green
                FG_NOTE          = new Color(131, 148, 150);  // base0
                FG_WRONG_GUESS   = new Color(255, 102, 102);  // bright red
            }
            case GRUVBOX_LIGHT -> {
                BG_GIVEN         = new Color(250, 245, 225);  // light bg
                BG_TO_GUESS      = new Color(255, 255, 255);
                BG_CORRECT_GUESS = new Color(244, 216, 155);
                BG_WRONG_GUESS   = new Color(251, 73, 52);
                BG_HIGH_CELL     = new Color(235, 226, 211);
                BG_HIGH_NUMBER   = new Color(230, 220, 200);
                BG_FOCUS_LOST    = new Color(245, 235, 220);

                FG_GIVEN         = new Color(60, 56, 54);     // dark fg
                FG_NOT_GIVEN     = new Color(146, 131, 116);
                FG_CORRECT_GUESS = new Color(152, 151, 26);   // green
                FG_NOTE          = new Color(168, 153, 132);
                FG_WRONG_GUESS   = new Color(204, 36, 29);    // bright red
            }
            case NEON_CYBERPUNK -> {
                BG_GIVEN         = new Color(20, 20, 30);
                BG_TO_GUESS      = new Color(10, 10, 20);
                BG_CORRECT_GUESS = new Color(0, 150, 255);
                BG_WRONG_GUESS   = new Color(255, 0, 90);
                BG_HIGH_CELL     = new Color(30, 0, 50);
                BG_HIGH_NUMBER   = new Color(60, 0, 90);
                BG_FOCUS_LOST    = new Color(25, 25, 40);

                FG_GIVEN         = new Color(0, 255, 200);
                FG_NOT_GIVEN     = new Color(150, 150, 255);
                FG_CORRECT_GUESS = new Color(0, 255, 180);
                FG_NOTE          = new Color(200, 100, 255);
                FG_WRONG_GUESS   = new Color(255, 60, 150);
            }
            case DRACULA -> {
                BG_GIVEN         = new Color(40, 42, 54);
                BG_TO_GUESS      = new Color(33, 34, 44);
                BG_CORRECT_GUESS = new Color(80, 250, 123);
                BG_WRONG_GUESS   = new Color(255, 85, 85);
                BG_HIGH_CELL     = new Color(68, 71, 90);
                BG_HIGH_NUMBER   = new Color(98, 114, 164);
                BG_FOCUS_LOST    = new Color(48, 50, 65);

                FG_GIVEN         = new Color(248, 248, 242);
                FG_NOT_GIVEN     = new Color(189, 189, 189);
                FG_CORRECT_GUESS = new Color(80, 250, 123);
                FG_NOTE          = new Color(144, 128, 255);
                FG_WRONG_GUESS   = new Color(255, 85, 85);
            }
       }
    }
}