package org.ln.noosudoku.view.cell;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * Base class for every visual Sudoku cell that holds shared coordinates and borders.
 */
@SuppressWarnings("serial")
public abstract class AbstractCell extends JPanel {

        protected int row;
        protected int col;
        CardCell cardCell;

        /**
         * Creates a cell positioned at the provided row and column.
         *
         * @param row zero-based row index
         * @param col zero-based column index
         */
        public AbstractCell(int row, int col) {
                super();
                this.row = row;
                this.col = col;
                Border border = new LineBorder(Color.GRAY);
                // Add thicker borders to delineate 3x3 sub-grids within the board.
                if ((col == 2 || col == 5) && (row == 2 || row == 5)) {
                        border = new MatteBorder(1, 1, 4, 4, Color.GRAY);
                } else if (col == 2 || col == 5) {
                        border = new MatteBorder(1, 1, 1, 4, Color.GRAY);
                } else if (row == 2 || row == 5) {
                        border = new MatteBorder(1, 1, 4, 1, Color.GRAY);
                }
                setBorder(border);
	}
	
        /**
         * Clears any user-facing state rendered by the cell implementation.
         */
        abstract void reset();

        /**
         * Returns the zero-based row index represented by this cell.
         *
         * @return the row index
         */
        public int getRow() {
                return row;
        }

        /**
         * Updates the zero-based row index metadata for this cell.
         *
         * @param row the row to set
         */
        public void setRow(int row) {
                this.row = row;
        }

        /**
         * Returns the zero-based column index represented by this cell.
         *
         * @return the column index
         */
        public int getCol() {
                return col;
        }

        /**
         * Updates the zero-based column index metadata for this cell.
         *
         * @param col the col to set
         */
        public void setCol(int col) {
                this.col = col;
        }

        /**
         * Returns the parent {@link CardCell} coordinating view and note panels.
         *
         * @return the parent card cell
         */
        public CardCell getCardCell() {
                return cardCell;
        }

        /**
         * Assigns the parent {@link CardCell} so the cell can access shared state.
         *
         * @param cardCell the card cell to set
         */
        public void setCardCell(CardCell cardCell) {
                this.cardCell = cardCell;
        }
	
	@Override
	public String toString() {
		return "AbstractCell [row=" + row + ", col=" + col + "]";
	}


}
