package org.ln.sudokuplus.view.cell;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;

@SuppressWarnings("serial")
public class NoteCell extends AbstractCell {

	JLabel[] fields = new JLabel[9];

	public NoteCell(int row, int col) {
		super(row, col);
		this.setLayout(new GridLayout(3, 3));

		for (int i = 1; i < 10; i++) {
			JLabel note = new JLabel();
			note.setText("");
			note.setHorizontalAlignment(SwingConstants.CENTER);
			fields[i-1] = note;
			fields[i-1].setForeground(SudokuConstants.FG_NOTE);
			add(note);
		}
	}

	@Override
	void reset() {
		for (int i = 0; i < fields.length; i++) {
			fields[i].setText("");
		}
	}

	/**
	 * @param s
	 * @param i
	 */
	public void setNoteText(String s, int i) {
		String res = fields[i].getText().compareTo("") == 0 ? s : "";
		fields[i].setText(res);
	}


	/**
	 * @param v
	 */
	public void setNoteText(int[] v) {
		for (int i = 0; i < v.length; i++) {
			if(v[i]> 0) {
				fields[i].setText(v[i]+"");
			}
		}
		getCardCell().setMode(CellMode.NOTEPANEL);
	}


	/**
	 * @param num
	 */
	public void removeNote(int num) {
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].getText().equals(Integer.toString(num))) {
				fields[i].setText("");
			}
		}
		
	}

}
