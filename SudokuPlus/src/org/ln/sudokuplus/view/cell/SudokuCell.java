package org.ln.sudokuplus.view.cell;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellStatus;

@SuppressWarnings("serial")
public class SudokuCell extends AbstractCell{
	

	   private int number;
	   private JLabel label;


	public SudokuCell(int row, int col) {
		super(row, col);
		//this.number = number;
		label = new JLabel();
		label.setFont(SudokuConstants.FONT_NUMBERS);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);
		//label.setText(row+"  "+col);
		add(label);
                setPreferredSize(new Dimension(SudokuConstants.CELL_SIZE, SudokuConstants.CELL_SIZE));
		
	}

	@Override
	void reset() {
		label.setText("");
	}
	
	@Override
	public String toString() {
		return "SudokuCell [row=" + row + ", col=" + col + ", number=" + number + "]";
	}
	
	
	/**
	 * @param number
	 * @return
	 */
	public boolean isCorrect(int number) {
		return number == this.number;
	}

/**
	 * @param text
	 * @see javax.swing.JLabel#setText(java.lang.String)
	 */
	public void setText(String text) {
		label.setText(text);
	}


	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
		label.setText(""+number);
		getCardCell().setStatus(CellStatus.GIVEN);
	}

	/**
	 * @return the label
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}



	
}
