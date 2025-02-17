package org.ln.sudokuplus.view.cell;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;
import org.ln.sudokuplus.model.SudokuConstants.CellStatus;

@SuppressWarnings("serial")
public class CardCell extends JPanel {

	protected int row;
	protected int col;

	private CardLayout cardLayout;
	private NoteCell noteCell;
	private SudokuCell sudokuCell;
	private CellStatus status;
	private CellMode mode;

	/**
	 * @param row
	 * @param col
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
		add(noteCell, CellMode.NOTEPANEL.toString());
		add(sudokuCell, CellMode.CELLPANEL.toString());
		setMode(CellMode.CELLPANEL);
		status = CellStatus.TO_GUESS;
	}

	public void resetCell() {
		sudokuCell.reset();
		noteCell.reset();
		setMode(CellMode.CELLPANEL);
		status = CellStatus.TO_GUESS;
	}
	
	public void setBold() {
		sudokuCell.getLabel().setFont(SudokuConstants.FONT_NUMBERS_BOLD);
	}
	
	
	public void setFontCell(Font f) {
		sudokuCell.getLabel().setFont(f);
	}

	/**
	 * 
	 */
	public void setHighlightCell(){
		setBackgroundCell(SudokuConstants.BG_HIGH_CELL);
	}

	/**
	 * 
	 */
	public void setSelectedCell(){
		setBackgroundCell(SudokuConstants.BG_HIGH_NUMBER);
	}	
	
	/**
	 * @param color
	 */
	public void setBackgroundCell(Color color){
		switch (mode) {
		case CELLPANEL: 
			sudokuCell.setBackground(color);
			break;
		case NOTEPANEL: 
			noteCell.setBackground(color);
			break;
		}
	}

	/**
	 *
	 */
	@Override
	public synchronized void addFocusListener(FocusListener l) {
		sudokuCell.addFocusListener(l);
		noteCell.addFocusListener(l);
	}


	/**
	 *
	 */
	@Override
	public synchronized void addMouseListener(MouseListener l) {
		sudokuCell.addMouseListener(l);
		noteCell.addMouseListener(l);
	}


	/**
	 *
	 */
	@Override
	public boolean requestFocusInWindow() {
		noteCell.requestFocusInWindow();
		sudokuCell.requestFocusInWindow();
		return true;
	}

	

	/**
	 * @param mode the mode to set
	 */
	public void setMode(CellMode mode) {
		this.mode = mode;
		cardLayout.show(this, mode.toString());
	}

	
	/**
	 * @return the mode
	 */
	public CellMode getMode() {
		return mode;
	}



	/**
	 * @return the noteCell
	 */
	public NoteCell getNoteCell() {
		return noteCell;
	}

	public int getNumber() {
		return sudokuCell.getNumber();
	}
	
	/**
	 * @param number
	 */
	public void setNumber(String number) {
		switch (status) {
		case TO_GUESS:
			sudokuCell.setText(number);
			setStatus(sudokuCell.isCorrect(Integer.parseInt(number))? 
					CellStatus.CORRECT_GUESS: CellStatus.WRONG_GUESS);
			break;

		case WRONG_GUESS:
			sudokuCell.setText(number);
			if(sudokuCell.isCorrect(Integer.parseInt(number)))
				setStatus(CellStatus.CORRECT_GUESS);
			break;
		default:
			break;
		}
	}


	/** This Cell paints itself based on its status */
	public void paint() {
		
		switch (status) {
		case GIVEN:
			sudokuCell.setText(sudokuCell.getNumber() + "");
			sudokuCell.setBackground(SudokuConstants.BG_GIVEN);
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_GIVEN);
			break;
		case TO_GUESS:
			sudokuCell.setText("");
			sudokuCell.setBackground(SudokuConstants.BG_TO_GUESS);
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_NOT_GIVEN);
			break;
		case CORRECT_GUESS:
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_CORRECT_GUESS);
			sudokuCell.setBackground(SudokuConstants.BG_CORRECT_GUESS);
			break;
		case WRONG_GUESS:
			sudokuCell.getLabel().setForeground(SudokuConstants.FG_WRONG_GUESS);
			sudokuCell.setBackground(SudokuConstants.BG_WRONG_GUESS);
			break;
		}
	}

	/**
	 * @param number
	 */
	public void setGiven(int number) {
		sudokuCell.setNumber(number);
	}

	/**
	 * @return the status
	 */
	public CellStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(CellStatus status) {
		this.status = status;
		paint();
	}

	
	
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}


	/**
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
