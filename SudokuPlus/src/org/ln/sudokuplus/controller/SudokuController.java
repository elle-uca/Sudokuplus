package org.ln.sudokuplus.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ln.sudokuplus.model.GameLevel;
import org.ln.sudokuplus.model.RathiTrisalGenerator;
import org.ln.sudokuplus.model.SudokuConstants;
import org.ln.sudokuplus.model.SudokuConstants.CellMode;
import org.ln.sudokuplus.model.SudokuConstants.CellStatus;
import org.ln.sudokuplus.view.SudokuView;
import org.ln.sudokuplus.view.cell.CardCell;

/**
 * Coordinates user interactions with the Sudoku view and underlying game state.
 */
public class SudokuController {

	private SudokuView view;
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
//	public void checkSolved() {
//		CardCell[][] cells = view.getBoard().getCardCells();
//		for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
//			for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//				if (cells[row][col].getStatus() == CellStatus.TO_GUESS
//						|| cells[row][col].getStatus() == CellStatus.WRONG_GUESS) {
//					return;
//				}
//			}
//		}
//		view.stopTimer();
//		view.getBoard().highlightSolved();
//	}


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
        //           public void removeDigits(CardCell[][] cells, int toGuess){
	//                   int count = toGuess;
	//                   int size = SudokuConstants.GRID_SIZE;
	//                   while (count != 0){
	//                           int cellId = ThreadLocalRandom.current().nextInt(size * size);
	//                           int i = (cellId / size);
	//                           int j = cellId % size;
	//                           if (cells[i][j].getStatus() == CellStatus.GIVEN){
	//                                   count--;
	//                                   cells[i][j].setStatus(CellStatus.TO_GUESS);
	//                           }
	//                   }
	//           }

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
				if((cells[row][col].getStatus() == CellStatus.GIVEN ||
						cells[row][col].getStatus() == CellStatus.CORRECT_GUESS) &&
						cells[row][col].getNumber() == n) {
					count++;
				}
			}
		}
                if(count == SudokuConstants.GRID_SIZE) {
			view.enableButtonsNumber(n-1, false);
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
         * Handles digit button presses and applies them to the currently selected cell.
         */
        public class ButtonNumberListener implements ActionListener {


                private SudokuController controller;



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
			if(cc == null)
				return;

                        // Depending on the current mode, store a note or a definitive value.
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
