package org.ln.sudokuplus.view;


import java.util.Vector;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;
import org.ln.sudokuplus.model.SudokuConstants.CellStatus;
import org.ln.sudokuplus.view.cell.CardCell;

public class Block {

	private Vector<CardCell> blockCells;
	

	public Block() {
		super();
		blockCells = new Vector<CardCell>(SudokuConstants.GRID_SIZE);
	}


	/**
	 * @param other
	 * @return
	 */
	public boolean contain(CardCell other){
		for (CardCell cell : blockCells) {
			if(cell.equals(other)) {
				return true;
			}
		}
		return  false;
	}
	
	
	public void removeNote(int num) {
		for (CardCell cell : blockCells) {
			if(cell.getMode().compareTo(CellMode.NOTEPANEL) == 0) {
				cell.getNoteCell().removeNote(num);
			}
			//cell.getNoteCell()
		}
	}
	
	/**
	 * @param v
	 */
	public void removeCandidates(int[] v){
		for (CardCell cell : blockCells) {
			if(cell.getStatus().equals(CellStatus.GIVEN) || cell.getStatus().equals(CellStatus.CORRECT_GUESS)) {
				//int x = cell.getNumber()-1;
				v[cell.getNumber()-1] = 0;
			}
		}
		
	}
	
	/**
	 *
	 */
	public void highlightBlock(){
		for (CardCell cell : blockCells) {
			cell.setHighlightCell();
		}
	}


	/**
	 * @param cardCell
	 */
	public void addCell(CardCell cardCell) {
		blockCells.add(cardCell);
		
	}


}
