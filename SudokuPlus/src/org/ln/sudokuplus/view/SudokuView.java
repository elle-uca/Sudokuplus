package org.ln.sudokuplus.view;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.ln.sudokuplus.controller.SudokuController;
import org.ln.sudokuplus.model.GameLevel;
import org.ln.sudokuplus.model.SudokuConstants;



/**
 * The main view of program
 */

@SuppressWarnings("serial")
public class SudokuView extends JFrame {

        private static final Logger LOGGER = Logger.getLogger(SudokuView.class.getName());

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
	private SudokuController controller;
	private Timer timer;
	private long startTime = -1;


	// Constructor
	public SudokuView() {
		controller = new SudokuController(this);
		comboLevel = new JComboBox<>(GameLevel.values());
		initNumberPanel();

		btnNote.setEnabled(false);
                btnNote.addActionListener(e -> {
                        //mode note On/Off
                        boolean b = btnNote.getText().compareTo("Note Off") == 0;
                        btnNote.setText(b ? "Note On" : "Note Off");
                        controller.setModeNote(b);
                });

                btnAdvNote.setEnabled(false);
                btnAdvNote.addActionListener(e -> {
                        board.setAdvancedNote();
                        //board.highlightSolved();
                });

                btnNewGame.addActionListener(
                                e -> controller.newGame((GameLevel) comboLevel.getSelectedItem()));

                timer = new Timer(1000, e -> {
                        if (startTime < 0) {
                                startTime = System.currentTimeMillis();
                        }
                        //long now = System.currentTimeMillis();
                        long clockTime = System.currentTimeMillis() - startTime;
                        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                        timeLabel.setText(df.format(clockTime));
                });

		levelLabel.setLabelFor(comboLevel);
		timeLabel.setText("00:00");
		JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20,5));
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
	 * Create number panel
	 */
	private void initNumberPanel() {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = new JButton((i+1)+"");
			numbers[i].setPreferredSize(new Dimension(60, 60));
			numbers[i].setFocusable(false);
			numbers[i].setEnabled(false);
			numbers[i].addActionListener(controller.new ButtonNumberListener(controller));
			numberPanel.add(numbers[i]);
		}
		numberPanel.setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, 60));
	}

        public void startTimer() {
                startTime = -1;
                timer.start();
                timeLabel.setText("00:00");
        }

	public void stopTimer() {
		timer.stop();
	}


	/**
	 * 
	 */
	public void enableButtons() {
		for (int i = 0; i < 9 ; i++) {
			enableButtonsNumber(i, true);
		}
		btnNote.setEnabled(true);
		btnAdvNote.setEnabled(true);

	}

	/**
	 * @param n
	 * @param bool
	 */
	public void enableButtonsNumber(int n, boolean bool) {
		numbers[n].setEnabled(bool);
	}


	/**
	 * @return the board
	 */
	public GameBoard getBoard() {
		return board;
	}



	/** The entry main()  method */
	public static void main(String args[]) {

                try {
                        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                }

                /* Create and display the form */
                EventQueue.invokeLater(() -> new SudokuView().setVisible(true));
        }

}

