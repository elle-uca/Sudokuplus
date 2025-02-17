package org.ln.sudokuplus.view.cell;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public abstract class AbstractCell extends JPanel{
	

	protected int row;
	protected int col;
	CardCell cardCell;



	public AbstractCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.setBorder(new LineBorder(Color.gray));
		if(col == 2 || col == 5) {
			this.setBorder( new MatteBorder(1, 1, 1, 4, Color.gray));
		}
		if(row == 2 || row == 5) {
			this.setBorder( new MatteBorder(1, 1, 4, 1, Color.gray));
		}
		if(row == 2 && col == 2) {
			this.setBorder( new MatteBorder(1, 1, 4, 4, Color.gray));
		}
		if(row == 2 && col == 5) {
			this.setBorder( new MatteBorder(1, 1, 4, 4, Color.gray));
		}
		if(row == 5 && col == 5) {
			this.setBorder( new MatteBorder(1, 1, 4, 4, Color.gray));
		}
		if(row == 5 && col == 2) {
			this.setBorder( new MatteBorder(1, 1, 4, 4, Color.gray));
		}
	}
	
	abstract void reset() ;

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * @return the cCell
	 */
	public CardCell getCardCell() {
		return cardCell;
	}

	/**
	 * @param cCell the cCell to set
	 */
	public void setCardCell(CardCell cardCell) {
		this.cardCell = cardCell;
	}
	
	@Override
	public String toString() {
		return "AbstractCell [row=" + row + ", col=" + col + "]";
	}


}
