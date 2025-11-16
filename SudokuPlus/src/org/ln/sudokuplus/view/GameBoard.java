package org.ln.sudokuplus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import org.ln.sudokuplus.enums.CellMode;
import org.ln.sudokuplus.enums.CellStatus;
import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.view.cell.AbstractCell;
import org.ln.sudokuplus.view.cell.CardCell;

/**
 * Swing panel responsible for rendering and managing the Sudoku grid cells.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	private CardCell selected;

	/** The game board composes of 9x9 CardCells  */
	private CardCell[][] cells = new CardCell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

	private Block[][] blocks = new Block[SudokuConstants.SUBGRID_SIZE][SudokuConstants.SUBGRID_SIZE];


	/** Constructor */
	public GameBoard() {
		setLayout(new GridLayout(SudokuConstants.GRID_SIZE,
				SudokuConstants.GRID_SIZE));
		setBorder(new MatteBorder(2, 2, 2, 2, Color.gray));
		setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, 
				SudokuConstants.BOARD_HEIGHT));

		// Allocate the 2D array of Blocks (3x3)
		for (int y = 0; y < SudokuConstants.SUBGRID_SIZE; y++) {
			for (int x = 0; x < SudokuConstants.SUBGRID_SIZE; x++) {
				//System.out.println("block     "+y+" "+x);
				blocks[y][x] = new Block();
			}
		}

		// Allocate the 2D array of CardCell (9x9), and added into panel
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col] = new CardCell(row, col);
				cells[row][col].addMouseListener(new CellListener()); 
				cells[row][col].addFocusListener(new HighlightListener());
				int blockRow = row / SudokuConstants.SUBGRID_SIZE;
				int blockCol = col / SudokuConstants.SUBGRID_SIZE;
				blocks[blockRow][blockCol].addCell(cells[row][col]);
				add(cells[row][col]);
			}
		}
	}


	/**
	 * Highlights row, column, block, and identical values related to the selected cell.
	 *
	 * @param c the cell that gained focus
	 */
	public void highlightRelatedCell(CardCell c) {
		// Reset every cell to its default appearance before applying highlights.
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].paint();
				cells[row][col].setFontCell(SudokuConstants.FONT_NUMBERS);
			}
		}

		/** highlight rows */
		for (int i = 0; i < cells.length; i++) {
			cells[c.getRow()][i].setHighlightCell();
		}

		/** highlight cols */
		for (CardCell[] cell : cells) {
			cell[c.getCol()].setHighlightCell();
		}

		/** highlight blocks */
		for (int y = 0; y < SudokuConstants.SUBGRID_SIZE; y++) {
			for (int x = 0; x < SudokuConstants.SUBGRID_SIZE; x++) {
				if(blocks[y][x].contain(c)) {
					blocks[y][x].highlightBlock();
				}
			}
		}

		/** highlight equal number */
		if(c.getStatus() == CellStatus.GIVEN || c.getStatus() == CellStatus.CORRECT_GUESS) {

			for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
				for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
					if(cells[row][col].getNumber() == c.getNumber()
							&& (cells[row][col].getStatus() == CellStatus.GIVEN
							|| cells[row][col].getStatus() == CellStatus.CORRECT_GUESS)) {
						cells[row][col].setBold();
					}
				}
			}
		}
		c.setSelectedCell();
	}


	/**
	 * Resets cell appearance before showing solved-state emphasis.
	 */
	public void highlightSolved() {
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].paint();
				cells[row][col].setFontCell(SudokuConstants.FONT_NUMBERS);
			}
		}

		//highlightRow(Color.black);
		//waiting();
		//highlightRow(Color.white);
		/** highlight rows */
	}



	/**
	 * Applies a background colour to the first row. Used mainly for visual feedback tests.
	 *
	 * @param c the colour to apply
	 */
	public void highlightRow(Color c) {
		for (int i = 0; i < cells.length; i++) {
			cells[0][i].setBackgroundCell(c);
		}

	}




	void waiting() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * Calculates and assigns pencil marks for each editable cell.
	 */
	public void setAdvancedNote() {
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				if(cells[row][col].getStatus().equals(CellStatus.TO_GUESS)) {
					searchCandidates(cells[row][col]);
				}
			}
		}
	}


	/**
	 * Computes candidate numbers for the given cell by excluding conflicting values.
	 *
	 * @param cell the cell whose pencil marks should be updated
	 */
	public void searchCandidates(CardCell cell) {
		int[] candidates = new int[SudokuConstants.GRID_SIZE];

		for (int x = 0; x < candidates.length; x++) {
			candidates[x] = x+1;
		}

		CardCell c;
		for (int i = 0; i < cells.length; i++) {
			// Remove numbers already confirmed in the same row.
			c = cells[cell.getRow()][i];
			if(c.getStatus().equals(CellStatus.GIVEN) || c.getStatus().equals(CellStatus.CORRECT_GUESS)){
				candidates[c.getNumber()-1] = 0;
			}
		}

		for (int j = 0; j < cells.length; j++) {
			// Remove numbers already confirmed in the same column.
			c = cells[j][cell.getCol()];
			if(c.getStatus().equals(CellStatus.GIVEN) || c.getStatus().equals(CellStatus.CORRECT_GUESS)){
				candidates[c.getNumber()-1] = 0;
			}
		}

		for (int y = 0; y < SudokuConstants.SUBGRID_SIZE; y++) {
			for (int x = 0; x < SudokuConstants.SUBGRID_SIZE; x++) {
				if(blocks[y][x].contain(cell)) {
					blocks[y][x].removeCandidates(candidates);
				}
			}
		}
		cell.getNoteCell().setNoteText(candidates);
	}

	/**
	 * Removes obsolete pencil marks around a cell once its value becomes confirmed.
	 *
	 * @param cell the cell that triggered the clean-up
	 */
	public void checkCancellNote(CardCell cell) {
		if(cell.getStatus().equals(CellStatus.CORRECT_GUESS)) {
			CardCell c;
			for (int i = 0; i < cells.length; i++) {
				// Remove numbers already confirmed in the same row.
				c = cells[cell.getRow()][i];
				if(c.getMode().compareTo(CellMode.NOTEPANEL) == 0){
					c.getNoteCell().removeNote(cell.getNumber()); 
				}
			}

			for (int j = 0; j < cells.length; j++) {
				// Remove numbers already confirmed in the same column.
				c = cells[j][cell.getCol()];
				if(c.getMode().compareTo(CellMode.NOTEPANEL) == 0){
					c.getNoteCell().removeNote(cell.getNumber()); 
				}
			}

			for (int y = 0; y < SudokuConstants.SUBGRID_SIZE; y++) {
				for (int x = 0; x < SudokuConstants.SUBGRID_SIZE; x++) {
					if(blocks[y][x].contain(cell)) {
						blocks[y][x].removeNote(cell.getNumber());
					}
				}
			}
		}
	}


	/**
	 * Returns the card cell that currently has focus.
	 *
	 * @return the selected card cell, or {@code null} when none is focused
	 */
	public CardCell getSelected() {
		return selected;
	}

	/**
	 * Provides read-only access to the board's card cell matrix.
	 *
	 * @return the grid of card cells composing the board
	 */
	public CardCell[][] getCardCells() {
		return cells;
	}

	/**
	 * 
	 */
	class CellListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println("clicked   "+e);
			AbstractCell pc = (AbstractCell) e.getSource();
			pc.requestFocusInWindow();
		}
	}

	class HighlightListener extends FocusAdapter {

		@Override
		public void focusGained(FocusEvent e) {
			AbstractCell c = (AbstractCell) e.getSource();
			selected = c.getCardCell();
			highlightRelatedCell(selected);
		}

		@Override
		public void focusLost(FocusEvent e) {
			AbstractCell pc = (AbstractCell) e.getSource();
			pc.setBackground(SudokuConstants.BG_FOCUS_LOST);
		}
	}






}
