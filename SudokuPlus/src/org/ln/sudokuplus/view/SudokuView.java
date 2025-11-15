package org.ln.sudokuplus.view;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.ln.sudokuplus.controller.SudokuController;
import org.ln.sudokuplus.model.GameLevel;
import org.ln.sudokuplus.model.SudokuConstants;

import com.formdev.flatlaf.FlatLightLaf;



/**
 * Main application window that orchestrates the game board and controls.
 */

@SuppressWarnings("serial")
public class SudokuView extends JFrame {

	//private static final Logger LOGGER = Logger.getLogger(SudokuView.class.getName());

	// private variables
	private GameBoard board = new GameBoard();
	private JButton btnNewGame = new JButton("New Game");
	private JButton btnNote = new JButton("Note Off");
	private JButton btnAdvNote = new JButton("Adv Note");
	private JLabel levelLabel = new JLabel("Level");
	private JLabel timeLabel = new JLabel("");
	private JComboBox<GameLevel> comboLevel;
	private JButton[] numbers = new JButton[SudokuConstants.GRID_SIZE];
	private JPanel numberPanel = new JPanel(new GridLayout(1, SudokuConstants.GRID_SIZE));
	private static final int LEVEL_PANEL_HGAP = 20;
	private static final int LEVEL_PANEL_VGAP = 5;

	private SudokuController controller;
	private Timer timer;
	private Instant startTime;
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss");


	/**
	 * Builds the main application window and initialises UI components.
	 */
	public SudokuView() {
		comboLevel = new JComboBox<>(GameLevel.values());
		initNumberPanel();

		btnNote.setEnabled(false);
		btnAdvNote.setEnabled(false);

		timer = new Timer(SudokuConstants.TIMER_DELAY_MILLIS, e -> {
			if (startTime == null) {
				return;
			}
			Duration elapsed = Duration.between(startTime, Instant.now());
			updateTimeLabel(elapsed);
		});

		levelLabel.setLabelFor(comboLevel);
		updateTimeLabel(Duration.ZERO);
		JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, LEVEL_PANEL_HGAP, LEVEL_PANEL_VGAP));
		levelPanel.add(btnNewGame);
		levelPanel.add(levelLabel);
		levelPanel.add(comboLevel);
		levelPanel.add(timeLabel);
		levelPanel.add(btnNote);
		levelPanel.add(btnAdvNote);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(levelPanel, BorderLayout.NORTH);
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(numberPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // to handle window-closing
		setTitle("SudokuPlus");
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Prepares the row of number buttons used for digit entry.
	 */
	private void initNumberPanel() {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = new JButton((i + 1) + "");
			numbers[i].setPreferredSize(new Dimension(SudokuConstants.CELL_SIZE, SudokuConstants.CELL_SIZE));
			numbers[i].setFocusable(false);
			numbers[i].setEnabled(false);
			numberPanel.add(numbers[i]);
		}
		numberPanel.setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, SudokuConstants.CELL_SIZE));
	}

	/**
	 * Wires UI events to the provided controller instance.
	 *
	 * @param controller orchestrator for board interactions
	 */
	public void setController(SudokuController controller) {
		this.controller = controller;

		btnNote.addActionListener(e -> {
			// Update the toggle label before notifying the controller.
			boolean noteModeEnabled = btnNote.getText().compareTo("Note Off") == 0;
			btnNote.setText(noteModeEnabled ? "Note On" : "Note Off");
			controller.setModeNote(noteModeEnabled);
		});

		btnAdvNote.addActionListener(e -> board.setAdvancedNote());

		btnNewGame.addActionListener(
				e -> this.controller.newGame((GameLevel) comboLevel.getSelectedItem()));

		for (JButton number : numbers) {
			number.addActionListener(this.controller.new ButtonNumberListener(this.controller));
		}
	}

	/**
	 * Starts the elapsed time tracking for the current puzzle.
	 */
	public void startTimer() {
		startTime = Instant.now();
		timer.start();
		updateTimeLabel(Duration.ZERO);
	}

	/**
	 * Stops the in-progress game timer.
	 */
	public void stopTimer() {
		timer.stop();
	}

	private void updateTimeLabel(Duration duration) {
		long seconds = Math.max(0, duration.getSeconds());
		long displaySeconds = seconds % Duration.ofDays(1).getSeconds();
		LocalTime displayTime = LocalTime.MIDNIGHT.plusSeconds(displaySeconds);
		timeLabel.setText(TIME_FORMATTER.format(displayTime));
	}


	/**
	 * Enables interaction controls once a game begins.
	 */
	public void enableButtons() {
		for (int i = 0; i < SudokuConstants.GRID_SIZE ; i++) {
			enableButtonsNumber(i, true);
		}
		btnNote.setEnabled(true);
		btnAdvNote.setEnabled(true);

	}

	/**
	 * Enables or disables the specified number button.
	 *
	 * @param n index of the button to update
	 * @param bool {@code true} to enable the button
	 */
	public void enableButtonsNumber(int n, boolean bool) {
		numbers[n].setEnabled(bool);
	}


	/**
	 * Exposes the board component for controller access.
	 *
	 * @return the game board displayed in the window
	 */
	public GameBoard getBoard() {
		return board;
	}



	/**
	 * Entry point that configures the look and feel before showing the view.
	 */
	public static void main(String args[]) {
		FlatLightLaf.setup();
//		try {
//			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			LOGGER.log(Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			LOGGER.log(Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			LOGGER.log(Level.SEVERE, null, ex);
//		} catch (UnsupportedLookAndFeelException ex) {
//			LOGGER.log(Level.SEVERE, null, ex);
//		}

		/* Create and display the form */
		EventQueue.invokeLater(() -> {
			SudokuView view = new SudokuView();
			SudokuController controller = new SudokuController(view);
			view.setController(controller);
			view.setVisible(true);
		});
	}

}

