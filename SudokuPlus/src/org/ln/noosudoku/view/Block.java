package org.ln.noosudoku.view;

import java.util.ArrayList;
import java.util.List;

import org.ln.noosudoku.enums.CellMode;
import org.ln.noosudoku.enums.CellStatus;
import org.ln.noosudoku.model.SudokuConstants;
import org.ln.noosudoku.view.cell.CardCell;

/**
 * Represents a 3x3 sub-grid and manages the cells that belong to it.
 */
public class Block {

	private final List<CardCell> blockCells;

	/**
	 * Creates an empty block ready to receive its member cells.
	 */
	public Block() {
		super();
		blockCells = new ArrayList<>(SudokuConstants.GRID_SIZE);
	}

	/**
	 * Checks whether the supplied cell is already registered within the block.
	 *
	 * @param other the cell being queried
	 * @return {@code true} if the block contains the cell
	 */
	public boolean contain(CardCell other) {
		return blockCells.contains(other);
	}

	/**
	 * Removes the provided note value from every note-enabled cell in this block.
	 *
	 * @param num note value to delete
	 */
	public void removeNote(int num) {
		for (CardCell cell : blockCells) {
			if (cell.getMode() == CellMode.NOTEPANEL) {
				cell.getNoteCell().removeNote(num);
			}
		}
	}

	/**
	 * Clears candidate values that are already solved inside this block.
	 *
	 * @param v candidate values indexed by value minus one
	 */
	public void removeCandidates(int[] v) {
		for (CardCell cell : blockCells) {
			if (cell.getStatus() == CellStatus.GIVEN || cell.getStatus() == CellStatus.CORRECT_GUESS) {
				// Set index to zero to indicate that the number cannot be reused.
				v[cell.getNumber() - 1] = 0;
			}
		}
	}

	/**
	 * Applies highlight styling to all cells in the block.
	 */
	public void highlightBlock() {
		blockCells.forEach(CardCell::setHighlightCell);
	}

	/**
	 * Adds a new cell to the list of cells that belong to this block.
	 *
	 * @param cardCell the cell to register
	 */
	public void addCell(CardCell cardCell) {
		blockCells.add(cardCell);
	}
}
