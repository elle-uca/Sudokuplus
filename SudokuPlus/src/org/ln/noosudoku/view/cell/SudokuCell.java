package org.ln.noosudoku.view.cell;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.ln.noosudoku.enums.CellStatus;
import org.ln.noosudoku.model.SudokuConstants;

/**
 * Visual component representing the primary Sudoku value inside the grid.
 */
@SuppressWarnings("serial")
public class SudokuCell extends AbstractCell {

        private int number;
        private JLabel label;
        /**
         * Creates a Sudoku cell for the given coordinates and configures its label.
         *
         * @param row zero-based row index
         * @param col zero-based column index
         */
        public SudokuCell(int row, int col) {
                super(row, col);
                label = new JLabel();
                label.setFont(SudokuConstants.FONT_NUMBERS);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
                add(label);
                setPreferredSize(new Dimension(SudokuConstants.CELL_SIZE, SudokuConstants.CELL_SIZE));

        }

        /**
         * Removes any displayed value from the label.
         */
        @Override
        void reset() {
                label.setText("");
        }

	@Override
	public String toString() {
		return "SudokuCell [row=" + row + ", col=" + col + ", number=" + number + "]";
	}
	
	
        /**
         * Checks whether the provided number matches the stored solution value.
         *
         * @param number candidate number to check
         * @return {@code true} if the guess equals the stored number
         */
        public boolean isCorrect(int number) {
                return number == this.number;
        }

        /**
         * Updates the label text used to display the current guess.
         *
         * @param text string representation of the value to display
         * @see javax.swing.JLabel#setText(java.lang.String)
         */
        public void setText(String text) {
                label.setText(text);
        }


        /**
         * Returns the stored solution number for this cell.
         *
         * @return the number
         */
        public int getNumber() {
                return number;
        }

        /**
         * Stores the solution number and marks the cell as a given.
         *
         * @param number the number to set
         */
        public void setNumber(int number) {
                this.number = number;
                label.setText(String.valueOf(number));
                getCardCell().setStatus(CellStatus.GIVEN);
        }

        /**
         * Returns the label used to render the cell value.
         *
         * @return the label
         */
        public JLabel getLabel() {
                return label;
        }

        /**
         * Replaces the label component used for rendering.
         *
         * @param label the label to set
         */
        public void setLabel(JLabel label) {
                this.label = label;
        }



	
}
