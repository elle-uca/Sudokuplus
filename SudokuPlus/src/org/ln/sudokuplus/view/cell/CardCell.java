package org.ln.sudokuplus.view.cell;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.ln.sudokuplus.enums.CellMode;
import org.ln.sudokuplus.enums.CellStatus;
import org.ln.sudokuplus.model.SudokuConstants;

/**
 * Composite panel containing both the main Sudoku cell and its note-taking panel.
 */
@SuppressWarnings("serial")
public class CardCell extends JPanel {

	protected int row;
	protected int col;

	private final CardLayout cardLayout;
	private final NoteCell noteCell;
	private final SudokuCell sudokuCell;
	private CellStatus status;
	private CellMode mode;

	/**
	 * Builds a card cell composed of note and sudoku panels for the given position.
	 *
	 * @param row zero-based row index
	 * @param col zero-based column index
	 */
	public CardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;

		noteCell = new NoteCell(row, col);
		noteCell.setCardCell(this);
		sudokuCell = new SudokuCell(row, col);
		sudokuCell.setCardCell(this);

		cardLayout = new CardLayout();
		setLayout(cardLayout);
		// Register both panels under the card layout so they can be toggled at runtime.
		add(noteCell, CellMode.NOTEPANEL.toString());
		add(sudokuCell, CellMode.CELLPANEL.toString());
		setMode(CellMode.CELLPANEL);
		status = CellStatus.TO_GUESS;
	}

	/**
	 * Restores the cell to its default, editable state.
	 */
	public void resetCell() {
		sudokuCell.reset();
		noteCell.reset();
		setMode(CellMode.CELLPANEL);
		status = CellStatus.TO_GUESS;
	}

	/**
	 * Applies the bold font to the number label for emphasis.
	 */
	public void setBold() {
		sudokuCell.getLabel().setFont(SudokuConstants.FONT_NUMBERS_BOLD);
	}


	/**
	 * Replaces the font used by the number label.
	 *
	 * @param f font applied to the number label
	 */
	public void setFontCell(Font f) {
		sudokuCell.getLabel().setFont(f);
	}

	/**
	 * Highlights the cell background to indicate a related cell.
	 */
	public void setHighlightCell(){
		setBackgroundCell(SudokuConstants.BG_HIGH_CELL);
	}

	/**
	 * Highlights the cell background to indicate direct selection.
	 */
	public void setSelectedCell(){
		setBackgroundCell(SudokuConstants.BG_HIGH_NUMBER);
	}

	/**
	 * Applies the provided background color to the currently visible panel.
	 *
	 * @param color new background color
	 */
	public void setBackgroundCell(Color color){
		// Apply the color to whichever card is active so focus styles stay consistent.
		switch (mode) {
			case CELLPANEL -> sudokuCell.setBackground(color);
			case NOTEPANEL -> noteCell.setBackground(color);
		}
	}

	/**
	 * Registers a focus listener on both underlying panels.
	 */
	@Override
	public synchronized void addFocusListener(FocusListener l) {
		sudokuCell.addFocusListener(l);
		noteCell.addFocusListener(l);
	}


	/**
	 * Registers a mouse listener on both underlying panels.
	 */
	@Override
	public synchronized void addMouseListener(MouseListener l) {
		sudokuCell.addMouseListener(l);
		noteCell.addMouseListener(l);
	}


	/**
	 * Ensures focus requests are propagated to both child panels.
	 */
	@Override
	public boolean requestFocusInWindow() {
		noteCell.requestFocusInWindow();
		sudokuCell.requestFocusInWindow();
		return true;
	}



	/**
	 * Shows either the note panel or the sudoku panel depending on the provided mode.
	 *
	 * @param mode the mode to set
	 */
	public void setMode(CellMode mode) {
		this.mode = mode;
		cardLayout.show(this, mode.toString());
	}


	/**
	 * Returns the current display mode of this card cell.
	 *
	 * @return the mode
	 */
	public CellMode getMode() {
		return mode;
	}



	/**
	 * Provides access to the note panel for additional customization.
	 *
	 * @return the note cell
	 */
	public NoteCell getNoteCell() {
		return noteCell;
	}

	/**
	 * Returns the number currently displayed by the sudoku panel.
	 *
	 * @return displayed number
	 */
	public int getNumber() {
		return sudokuCell.getNumber();
	}

	/**
	 * Updates the guessed number and refreshes the status accordingly.
	 *
	 * @param number guess entered by the user
	 */
	public void setNumber(String number) {
		switch (status) {
		case TO_GUESS -> {
			sudokuCell.setText(number);
			int parsedNumber = Integer.parseInt(number);
			setStatus(sudokuCell.isCorrect(parsedNumber) ?
					CellStatus.CORRECT_GUESS : CellStatus.WRONG_GUESS);
		}

		case WRONG_GUESS -> {
			sudokuCell.setText(number);
			int parsedNumber = Integer.parseInt(number);
			if (sudokuCell.isCorrect(parsedNumber)) {
				setStatus(CellStatus.CORRECT_GUESS);
			}
		}
		default -> {}

		}
	}


	/**
	 * Updates colors and text based on the current {@link CellStatus}.
	 */
	public void paint() {

		switch (status) {
		case GIVEN -> {
			sudokuCell.setText(sudokuCell.getNumber() + "");
			sudokuCell.setBackground(SudokuConstants.BG_GIVEN);
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_GIVEN);
		}
		case TO_GUESS -> {
			sudokuCell.setText("");
			sudokuCell.setBackground(SudokuConstants.BG_TO_GUESS);
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_NOT_GIVEN);
		}
		case CORRECT_GUESS -> {
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_CORRECT_GUESS);
			sudokuCell.setBackground(SudokuConstants.BG_CORRECT_GUESS);
		}
		case WRONG_GUESS -> {
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_WRONG_GUESS);
			sudokuCell.setBackground(SudokuConstants.BG_WRONG_GUESS);
		}
		
		}
	}

	/**
	 * Marks the cell as given and stores the fixed number.
	 *
	 * @param number provided value from the puzzle definition
	 */
	public void setGiven(int number) {
		sudokuCell.setNumber(number);
	}

	/**
	 * Returns the current status associated with the cell.
	 *
	 * @return the status
	 */
	public CellStatus getStatus() {
		return status;
	}


	/**
	 * Updates the cell status and repaints the cell to match the new state.
	 *
	 * @param status the status to set
	 */
	public void setStatus(CellStatus status) {
		this.status = status;
		paint();
	}



	/**
	 * Returns the zero-based row index for this cell.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}


	/**
	 * Returns the zero-based column index for this cell.
	 *
	 * @return the col
	 */
	public int getCol() {
		return col;
	}



	@Override
	public String toString() {
		return "CardCell [row=" + row + ", col=" + col + ", status=" + status + ", mode=" + mode + "]";
	}

}
