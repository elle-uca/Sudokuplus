package org.ln.sudokuplus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.ln.sudokuplus.model.Level;
import org.ln.sudokuplus.model.RathiTrisalGenerator;
import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;
import org.ln.sudokuplus.model.SudokuConstants.CellStatus;
import org.ln.sudokuplus.view.SudokuView;
import org.ln.sudokuplus.view.cell.CardCell;

public class SudokuController {

	private SudokuView view;
	private boolean modeNote = false;



	/**
	 * @param view
	 */
	public SudokuController(SudokuView view) {
		this.view = view;
	}

	/**
	 * Check if the puzzle is solved
	 * none of the cell have status of TO_GUESS or WRONG_GUESS
	 */
	public void checkSolved() {
		CardCell[][] cells = view.getBoard().getCardCells();
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				if (cells[row][col].getStatus() == CellStatus.TO_GUESS
						|| cells[row][col].getStatus() == CellStatus.WRONG_GUESS) {
					return;
				}
			}
		}
		System.out.println("Solveeeeeeeeeeeeeeeeeed  ");
		view.stopTimer();
		view.getBoard().highlightSolved();
	}

	/**
	 * Generate a new puzzle; and reset the game board of cells based on the puzzle.
	 * You can call this method to start a new game.
	 * @param object
	 */
	public void newGame(Level level) {
		CardCell[][] cells = view.getBoard().getCardCells();
		RathiTrisalGenerator gen = new RathiTrisalGenerator();

		int[][] gameMat = gen.getPuzzle();

		// Initialize all the 9x9 cells, based on the puzzle.
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				cells[row][col].resetCell();
				cells[row][col].setGiven(gameMat[row][col]);
			}
		}
		removeDigits(cells, level.getToGuess());
		view.enableButtons();
		checkDisableButtons();
		view.startTimer();
	}

	   // Random generator
	   int randomGenerator(int num){
	       return (int) Math.floor((Math.random() * num + 1));
	   }

	   /**
	 * @param cells
	 * @param toGuess
	 */
	   public void removeDigits(CardCell[][] cells, int toGuess){
		   int count = toGuess;
		   int size = SudokuConstants.GRID_SIZE;
		   while (count != 0){
			   int cellId = randomGenerator(size * size)-1;
			   int i = (cellId / size);
			   int j = cellId % size;
			   if (cells[i][j].getStatus() == CellStatus.GIVEN){
				   count--;
				   cells[i][j].setStatus(CellStatus.TO_GUESS);
			   }
		   }
	   }



	/**
	 * At new game
	 */
	public void checkDisableButtons() {
		for (int i = 1; i < 10 ; i++) {
			checkDisableButton(i);
		}
	}

	/**
	 * @param n
	 */
	public void checkDisableButton(int n) {
		CardCell[][] cells = view.getBoard().getCardCells();
		int count = 0;
		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
				if((cells[row][col].getStatus() == CellStatus.GIVEN ||
						cells[row][col].getStatus() == CellStatus.CORRECT_GUESS) &&
						cells[row][col].getNumber() == n) {
					count++;
				}
			}
		}
		if(count == 9) {
			view.enableButtonsNumber(n-1, false);
		}
	}


	/**
	 * @return the modeNote
	 */
	public boolean isModeNote() {
		return modeNote;
	}

	
	/**
	 * @param modeNote the modeNote to set
	 */
	public void setModeNote(boolean modeNote) {
		this.modeNote = modeNote;
	}
	
	
	/**
	 * @return the view
	 */
	public SudokuView getView() {
		return view;
	}
	
	public class ButtonNumberListener implements ActionListener {


		private SudokuController controller;



		public ButtonNumberListener(SudokuController controller) {
			super();
			this.controller = controller;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("action  "+e.getActionCommand());
			CardCell cc = controller.getView().getBoard().getSelected();
			if(cc == null)
				return;
			
			// due possibilità
			System.out.println("mode  "+controller.isModeNote());
			if(controller.isModeNote()) {
				cc.setMode(CellMode.NOTEPANEL);
				cc.getNoteCell().setNoteText(e.getActionCommand(), Integer.parseInt(e.getActionCommand())-1);
			}else {
				cc.setMode(CellMode.CELLPANEL);
				cc.setNumber(e.getActionCommand());
				
				controller.checkDisableButton(Integer.parseInt(e.getActionCommand()));
				controller.getView().getBoard().checkCancellNote(cc);
				controller.checkSolved();
			}
		}
	}

}
