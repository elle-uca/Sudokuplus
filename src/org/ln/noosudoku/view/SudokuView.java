package org.ln.noosudoku.view;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.ln.noosudoku.controller.SudokuController;
import org.ln.noosudoku.enums.Theme;
import org.ln.noosudoku.model.GameLevel;
import org.ln.noosudoku.model.SudokuConstants;

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
	private final EnumMap<GameLevel, JRadioButtonMenuItem> levelMenuItems = new EnumMap<>(GameLevel.class);
	private final EnumMap<Theme, JRadioButtonMenuItem> themeMenuItems = new EnumMap<>(Theme.class);
	private JCheckBoxMenuItem notesToggleMenuItem;
	private JMenuItem suggestMenuItem;
	private JMenuItem solveMenuItem;
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


		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);
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

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu newGameMenu = new JMenu("Nuova partita");
		ButtonGroup levelGroup = new ButtonGroup();
		for (GameLevel level : GameLevel.values()) {
			JRadioButtonMenuItem levelItem = new JRadioButtonMenuItem(level.getTitle());
			levelItem.setSelected(level == comboLevel.getSelectedItem());
			levelItem.addActionListener(e -> startNewGame(level));
			levelGroup.add(levelItem);
			newGameMenu.add(levelItem);
			levelMenuItems.put(level, levelItem);
		}
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(newGameMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);

		JMenu viewMenu = new JMenu("View");
		JMenu themeMenu = new JMenu("Tema");
		ButtonGroup themeGroup = new ButtonGroup();
		for (Theme theme : Theme.values()) {
			String label = theme.name().replace('_', ' ');
			JRadioButtonMenuItem themeItem = new JRadioButtonMenuItem(label);
			themeItem.setSelected(theme == ThemeSupport.getTheme());
			themeItem.addActionListener(e -> updateTheme(theme));
			themeGroup.add(themeItem);
			themeMenu.add(themeItem);
			themeMenuItems.put(theme, themeItem);
		}
		viewMenu.add(themeMenu);
		menuBar.add(viewMenu);

		JMenu toolsMenu = new JMenu("Tool");
		notesToggleMenuItem = new JCheckBoxMenuItem("Attiva note");
		notesToggleMenuItem.addActionListener(e -> toggleNotes());
		toolsMenu.add(notesToggleMenuItem);
		suggestMenuItem = new JMenuItem("Suggerisci cella");
		suggestMenuItem.addActionListener(e -> suggestSelectedCell());
		toolsMenu.add(suggestMenuItem);
		solveMenuItem = new JMenuItem("Risolvi");
		solveMenuItem.addActionListener(e -> solveCurrentGame());
		toolsMenu.add(solveMenuItem);
		menuBar.add(toolsMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem preferencesItem = new JMenuItem("Preferenze...");
		preferencesItem.addActionListener(e -> showPreferencesDialog());
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(e -> showAboutDialog());
		helpMenu.add(preferencesItem);
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);

		return menuBar;
	}

	/**
	 * Wires UI events to the provided controller instance.
	 *
	 * @param controller orchestrator for board interactions
	 */
	public void setController(SudokuController controller) {
		this.controller = controller;
		btnNote.addActionListener(e -> toggleNotes());
		btnAdvNote.addActionListener(e -> board.setAdvancedNote());
		btnNewGame.addActionListener(
				e -> startNewGame((GameLevel) comboLevel.getSelectedItem()));

		for (JButton number : numbers) {
			number.addActionListener(this.controller.new ButtonNumberListener(this.controller));
		}
		updateNotesUi(controller.isModeNote());
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

	private void startNewGame(GameLevel level) {
		if (controller == null) {
			return;
		}
		comboLevel.setSelectedItem(level);
		selectLevelMenuItem(level);
		controller.newGame(level);
	}

	private void selectLevelMenuItem(GameLevel level) {
		JRadioButtonMenuItem item = levelMenuItems.get(level);
		if (item != null) {
			item.setSelected(true);
		}
	}

	private void updateTheme(Theme theme) {
		ThemeSupport.setTheme(theme);
		selectThemeMenuItem(theme);
		board.refreshTheme();
	}

	private void selectThemeMenuItem(Theme theme) {
		JRadioButtonMenuItem item = themeMenuItems.get(theme);
		if (item != null) {
			item.setSelected(true);
		}
	}

	private void toggleNotes() {
		if (controller == null) {
			return;
		}
		boolean enabled = !controller.isModeNote();
		controller.setModeNote(enabled);
		updateNotesUi(enabled);
	}

	private void suggestSelectedCell() {
		if (controller == null) {
			return;
		}
		controller.suggest();
	}

	private void solveCurrentGame() {
		if (controller == null) {
			return;
		}
		controller.solve();
	}

	private void updateNotesUi(boolean enabled) {
		btnNote.setText(enabled ? "Note On" : "Note Off");
		if (notesToggleMenuItem != null) {
			notesToggleMenuItem.setSelected(enabled);
			notesToggleMenuItem.setText(enabled ? "Disattiva note" : "Attiva note");
		}
	}

	private void showPreferencesDialog() {
		JDialog dialog = new JDialog(this, "Preferenze", true);
		dialog.setContentPane(new PreferencesPanel());
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	private void showAboutDialog() {
		JDialog dialog = new JDialog(this, "Informazioni", true);
		dialog.setContentPane(new AboutPanel());
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}



	/**
	 * Entry point that configures the look and feel before showing the view.
	 */
	public static void main(String args[]) {
		// 1) CREO E MOSTRO SUBITO LO SPLASH 
		SplashScreen splash = new SplashScreen();
		splash.showSplash();

		// 2) FALSO CARICAMENTO MENTRE LO SPLASH È VISIBILE
		for (int i = 0; i <= 100; i++) {
			splash.setProgress(i, "Caricamento... " + i + "%");
			try {
				Thread.sleep(15);     // <-- tempo per permettere allo splash di essere visualizzato
			} catch (InterruptedException ignored) {}
		}

		/* Create and display the form */
		SwingUtilities.invokeLater(() -> {
			FlatLightLaf.setup();

			ThemeSupport.setTheme(Theme.CLASSIC);
			SudokuView view = new SudokuView();
			SudokuController controller = new SudokuController(view);
			view.setController(controller);
			view.setVisible(true);
			splash.close();

		});
	}

}

