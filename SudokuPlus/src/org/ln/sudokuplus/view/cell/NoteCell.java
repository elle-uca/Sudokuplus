package org.ln.sudokuplus.view.cell;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;

/**
 * Visual component showing small candidate notes for a Sudoku cell.
 */
@SuppressWarnings("serial")
public class NoteCell extends AbstractCell {

        private final JLabel[] fields = new JLabel[SudokuConstants.GRID_SIZE];

        /**
         * Creates the grid of candidate labels for the provided location.
         *
         * @param row zero-based row index
         * @param col zero-based column index
         */
        public NoteCell(int row, int col) {
                super(row, col);
                this.setLayout(new GridLayout(SudokuConstants.SUBGRID_SIZE, SudokuConstants.SUBGRID_SIZE));

                for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                        JLabel note = new JLabel();
                        note.setText("");
                        note.setHorizontalAlignment(SwingConstants.CENTER);
                        fields[i] = note;
                        fields[i].setForeground(SudokuConstants.FG_NOTE);
                        add(note);
                }
        }

        /**
         * Clears all candidate labels displayed in this note cell.
         */
        @Override
        void reset() {
                for (JLabel field : fields) {
                        field.setText("");
                }
        }

        /**
         * Toggles a single candidate in the note grid.
         *
         * @param s text to display when the slot is empty
         * @param i zero-based index for the candidate slot
         */
        public void setNoteText(String s, int i) {
                String result = fields[i].getText().isEmpty() ? s : "";
                fields[i].setText(result);
        }


        /**
         * Populates the note grid with the provided candidate array.
         *
         * @param v array of candidate values where zero represents an empty slot
         */
        public void setNoteText(int[] v) {
                for (int i = 0; i < v.length; i++) {
                        fields[i].setText(v[i] > 0 ? Integer.toString(v[i]) : "");
                }
                // Switching the card keeps the note panel visible when bulk values are set.
                getCardCell().setMode(CellMode.NOTEPANEL);
        }


        /**
         * Removes any occurrence of the provided number from the note grid.
         *
         * @param num candidate value to clear
         */
        public void removeNote(int num) {
                String target = Integer.toString(num);
                for (JLabel field : fields) {
                        if (target.equals(field.getText())) {
                                field.setText("");
                        }
                }

        }

}
