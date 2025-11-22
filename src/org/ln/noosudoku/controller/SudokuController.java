package org.ln.noosudoku.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ln.noosudoku.enums.CellMode;
import org.ln.noosudoku.enums.CellStatus;
import org.ln.noosudoku.model.GameLevel;
import org.ln.noosudoku.model.RathiTrisalGenerator;
import org.ln.noosudoku.model.SudokuConstants;
import org.ln.noosudoku.view.SudokuView;
import org.ln.noosudoku.view.cell.CardCell;

/**
 * Coordinates user interactions with the Sudoku view and underlying game state.
 * 
 * Author: Luca Noale
 */
public class SudokuController {

       private final SudokuView view;
       private boolean modeNote = false;



	/**
	 * Creates a controller bound to the supplied Sudoku view.
	 *
	 * @param view the UI facade used to display and interact with the game
	 */
	public SudokuController(SudokuView view) {
		this.view = view;
	}



	/**
	 * Checks whether every cell in the grid is resolved correctly and ends the game
	 * if so.
	 */
	public void checkSolved() {
		CardCell[][] cells = view.getBoard().getCardCells();

		// Determine whether any cell still requires input or is marked incorrect.
		boolean allSolved = Arrays.stream(cells)
				.flatMap(Arrays::stream)
				.noneMatch(cell -> cell.getStatus() == CellStatus.TO_GUESS
				|| cell.getStatus() == CellStatus.WRONG_GUESS);

		if (allSolved) {
			view.stopTimer();
			view.getBoard().highlightSolved();
		}
	}


	/**
	 * Generates a new puzzle, fills the board with the given numbers, and prepares
	 * the view for a fresh game session.
	 *
	 * @param gameLevel the difficulty level that determines how many digits to hide
	 */
	public void newGame(GameLevel gameLevel) {
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
		removeDigits(cells, gameLevel.getToGuess());
		view.enableButtons();
		checkDisableButtons();
		view.startTimer();
	}


	/**
	 * Hides a subset of digits by marking the corresponding cells as values to
	 * guess.
	 *
	 * @param cells   the grid of card cells used by the view
	 * @param toGuess how many digits should be hidden for the player to deduce
	 */
	public void removeDigits(CardCell[][] cells, int toGuess) {
		int size = SudokuConstants.GRID_SIZE;
		int total = size * size;

		// Create a list of all cell indexes in the board [0..80].
		List<Integer> indices = IntStream.range(0, total)
				.boxed()
				.collect(Collectors.toList());

		// Shuffle the index list to randomize the cells that will be emptied.
		Collections.shuffle(indices, ThreadLocalRandom.current());

		// Mark the first selected indexes as values the player must guess.
		for (int k = 0; k < toGuess && k < total; k++) {
			int cellId = indices.get(k);
			int i = cellId / size;
			int j = cellId % size;

			if (cells[i][j].getStatus() == CellStatus.GIVEN) {
				cells[i][j].setStatus(CellStatus.TO_GUESS);
			}
		}
	}


	/**
	 * Re-evaluates number buttons so they reflect the current state at the start of
	 * a new game.
	 */
	public void checkDisableButtons() {
		for (int i = 1; i <= SudokuConstants.GRID_SIZE ; i++) {
			checkDisableButton(i);
		}
	}

	/**
	 * Disables a number button if all of its digits are already placed on the
	 * board.
	 *
	 * @param n the digit to evaluate
	 */
	public void checkDisableButton(int n) {
		CardCell[][] cells = view.getBoard().getCardCells();
		int count = 0;
               for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                       for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                               CardCell cell = cells[row][col];
                               CellStatus status = cell.getStatus();
                               if ((status == CellStatus.GIVEN || status == CellStatus.CORRECT_GUESS)
                                               && cell.getNumber() == n) {
                                       count++;
                               }
                       }
               }
               if (count == SudokuConstants.GRID_SIZE) {
                       view.enableButtonsNumber(n - 1, false);
               }
        }


	/**
	 * Reports whether the controller is currently in note-taking mode.
	 *
	 * @return {@code true} if note mode is active; {@code false} otherwise
	 */
	public boolean isModeNote() {
		return modeNote;
	}


	/**
	 * Enables or disables note-taking mode for subsequent interactions.
	 *
	 * @param modeNote {@code true} to activate note mode; {@code false} to place
	 *                 digits directly
	 */
	public void setModeNote(boolean modeNote) {
		this.modeNote = modeNote;
	}


	/**
	 * Returns the bound Sudoku view.
	 *
	 * @return the view used by this controller
	 */
        public SudokuView getView() {
                return view;
        }


        /**
         * Fills the selected editable cell with the correct solution value.
         */
        public void suggest() {
                CardCell selected = view.getBoard().getSelected();
                if (selected == null || selected.getStatus() == CellStatus.GIVEN) {
                        return;
                }

                selected.setMode(CellMode.CELLPANEL);
                selected.setNumber(String.valueOf(selected.getNumber()));

                checkDisableButton(selected.getNumber());
                view.getBoard().checkCancellNote(selected);
                checkSolved();
        }


        /**
         * Completes the current puzzle by filling every editable cell with the correct
         * answer.
         */
        public void solve() {
                CardCell[][] cells = view.getBoard().getCardCells();

                for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                        for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                                CardCell cell = cells[row][col];
                                if (cell.getStatus() != CellStatus.GIVEN) {
                                        cell.setMode(CellMode.CELLPANEL);
                                        cell.setNumber(String.valueOf(cell.getNumber()));
                                }
                        }
                }

                checkDisableButtons();
                checkSolved();
        }

        /**
         * Handles digit button presses and applies them to the currently selected cell.
         */
        public class ButtonNumberListener implements ActionListener {

               private final SudokuController controller;

		/**
		 * Creates a listener that delegates button actions to the provided controller.
		 *
		 * @param controller the controller that manages board interactions
		 */
		public ButtonNumberListener(SudokuController controller) {
			super();
			this.controller = controller;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
                       CardCell cc = controller.getView().getBoard().getSelected();
                       if (cc == null) {
                               return;
                       }

                       String command = e.getActionCommand();
                       int number = Integer.parseInt(command);

                       // Depending on the current mode, store a note or a definitive value.
                       if (controller.isModeNote()) {
                               cc.setMode(CellMode.NOTEPANEL);
                               cc.getNoteCell().setNoteText(command, number - 1);
                       } else {
                               cc.setMode(CellMode.CELLPANEL);
                               cc.setNumber(command);

                               controller.checkDisableButton(number);
                               controller.getView().getBoard().checkCancellNote(cc);
                               controller.checkSolved();
                       }
                }
        }

}
